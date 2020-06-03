package com.leyou.controlloer;

import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.common.PageResult;
import com.leyou.item.Goods;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.pojo.SearchRequest;
import com.leyou.pojo.SearchResult;
import com.leyou.repository.GoodsRepository;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {
    /**
     *
     */
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    BrandClient brandClient;

    @RequestMapping("page")
    public PageResult<Goods> page(@RequestBody SearchRequest searchRequest){
        System.out.println(searchRequest.getKey()+"====="+searchRequest.getPage());
        //去es索引库做查询
        NativeSearchQueryBuilder builder=new NativeSearchQueryBuilder();
        //构造条件
        builder.withQuery(QueryBuilders.matchQuery("all",searchRequest.getKey()).operator(Operator.AND));
        //分页查询
        builder.withPageable(PageRequest.of(searchRequest.getPage()-1,searchRequest.getSize()));
        //根据新品排序
        builder.withSort(SortBuilders.fieldSort(searchRequest.getSortBy()).order(searchRequest.isDescending()? SortOrder.DESC:SortOrder.ASC));
        //加载分类和品牌
        String categoryName="categoryName";
        String brandName="brandName";
        NativeSearchQueryBuilder builder1 = builder.addAggregation(AggregationBuilders.terms(categoryName).field("cid3"));//聚合分类
        builder.addAggregation(AggregationBuilders.terms(brandName).field("brandId"));
        AggregatedPage<Goods> search = (AggregatedPage<Goods>)goodsRepository.search(builder.build());
        //构造分类信息--根据分类id获取名称
        LongTerms categoryAgg=(LongTerms)search.getAggregation(categoryName);
        List<Category> categoryList=new ArrayList<>();
        categoryAgg.getBuckets().forEach(bucket -> {
            Long categoryId=(Long)bucket.getKey();
            //根据分类id去数据库查询分类名称
            Category category=categoryClient.findCategoryById(categoryId);
            categoryList.add(category);
        });
        //构造品牌信息--根据品牌id获取名称
        LongTerms brandAgg =(LongTerms) search.getAggregation(brandName);
        List<Brand> brandList=new ArrayList<>();
        brandAgg.getBuckets().forEach(bucket -> {
            Long brandId=(Long)bucket.getKey();
            //根据品牌id查询品牌名称
            Brand brand=brandClient.findBrandById(brandId);
            brandList.add(brand);
        });

        //执行查询
        //Page<Goods> search = goodsRepository.search(builder.build());
        return new SearchResult(search.getTotalElements(),search.getContent(),search.getTotalPages(),categoryList,brandList);
    }
}
