package com.coupang.WEBPS007.kmincho;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Category {
    public static void main(String[] args) {
        ArrayList<SmallCategory> mSmallCategoryList = new ArrayList<SmallCategory>();
        ArrayList<BigCategory> mBigCategoryList = new ArrayList<BigCategory>();
        ArrayList<Product> mProductList = new ArrayList<Product>();
        
        int mNumSmallCategory;
        int mNumBigCategory;
        int mNumProduct;
        
        JSONParser parser = new JSONParser();
        
        try {
            Object obj = parser.parse(new FileReader("cate_product.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray smallCategoryList = (JSONArray) jsonObject
                    .get("smallCategorys");
            JSONArray bigCategoryList = (JSONArray) jsonObject
                    .get("bigCategorys");
            JSONArray productList = (JSONArray) jsonObject.get("products");

            mNumSmallCategory = smallCategoryList.size();
            mNumBigCategory = bigCategoryList.size();
            mNumProduct = productList.size();

            JSONObject jObj;
            
            //small category
            Iterator<JSONObject> iteratorSmall = smallCategoryList.iterator();
            while (iteratorSmall.hasNext()) {
                jObj = iteratorSmall.next();
                String cateId = (String) jObj.get("cateId");
                String cateName = (String) jObj.get("cateName");
                mSmallCategoryList.add(new SmallCategory(cateId, cateName));
            }
            
            //big category
            Iterator<JSONObject> iteratorBig = bigCategoryList.iterator();
            while (iteratorBig.hasNext()) {
                jObj = iteratorBig.next();
                String cateId = (String) jObj.get("cateId");
                String cateName = (String)jObj.get("cateName");
                
                JSONArray subCategoryList = (JSONArray)jObj.get("subCategorys");
                String[] subCate = new String[subCategoryList.size()];
                for (int i = 0; i < subCategoryList.size(); i++) {
                    subCate[i] = (String)subCategoryList.get(i);
                }
                mBigCategoryList.add(new BigCategory(cateId, cateName, subCate));
            }

            //Make TopNList for Small Category
            HashMap<String, TopNList> mTopListMapSmall = new HashMap<String, TopNList>();
            for (int i = 0; i < mSmallCategoryList.size(); i++) {
                mTopListMapSmall.put(mSmallCategoryList.get(i).mCateId, new TopNList());
            }
            
            //Make TopNList for Big Category
            HashMap<String, TopNList> mTopListMapBig = new HashMap<String, TopNList>();
            for (int i = 0; i < mBigCategoryList.size(); i++) {
                mTopListMapBig.put(mBigCategoryList.get(i).mCateId, new TopNList());
            }

            Iterator<JSONObject> iteratorProduct = productList.iterator();
            while (iteratorProduct.hasNext()) {
                jObj = iteratorProduct.next();
                String price = (String) jObj.get("price");
                String prodId = (String) jObj.get("prodId");
                String smallCategoryId = (String) jObj.get("smallCategoryId");
                String saleCnt = (String) jObj.get("saleCnt");
                
                Product product = new Product(Integer.valueOf(price), 
                                                prodId, 
                                                smallCategoryId, 
                                                Integer.valueOf(saleCnt));
                
                mTopListMapSmall.get(smallCategoryId).push(product);
                
                for (int i = 0; i < mBigCategoryList.size(); i++) {
                    String[] subCate = mBigCategoryList.get(i).mSubCategories;
                    for (int j = 0; j < subCate.length; j++) {
                        if (smallCategoryId.equals(subCate[j])) {
                            mTopListMapBig.get(mBigCategoryList.get(i).mCateId).push(product);
                        }
                    }
                }
            }

            for (int i = 0; i < mSmallCategoryList.size(); i++) {
                StringBuffer out = new StringBuffer();
                out.append(mSmallCategoryList.get(i).mCateId);
                out.append("::");
                out.append(mSmallCategoryList.get(i).mCateName);
                out.append("::");
                out.append( mTopListMapSmall.get(mSmallCategoryList.get(i).mCateId).getProductId());
                System.out.println(out.toString());
            }
            
            for (int i = 0; i < mBigCategoryList.size(); i++) {
                StringBuffer out = new StringBuffer();
                out.append(mBigCategoryList.get(i).mCateId);
                out.append("::");
                out.append(mBigCategoryList.get(i).mCateName);
                out.append("::");
                out.append( mTopListMapBig.get(mBigCategoryList.get(i).mCateId).getProductId());
                System.out.println(out.toString());
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void test0() {
//        System.out.println(mBigCategoryList.get(2).mCateId + ", "
//                + mBigCategoryList.get(2).mCateName + ", "
//                + mBigCategoryList.get(2).mSubCategories[0]);
//        System.out.println(mProductList.get(100).mPrice + ", "
//                + mProductList.get(100).mSaleCnt);
    }
    static void test1() {
        TopNList topList = new TopNList();
//      topList.push(new Product(100, "P001", "A", 20));
//      topList.push(new Product(100, "P002", "A", 300));
//      topList.push(new Product(100, "P003", "A", 100));
//      topList.push(new Product(100, "P004", "A", 50));
//      topList.push(new Product(100, "P005", "A", 100));
//      topList.push(new Product(100, "P006", "A", 80));
//      topList.push(new Product(100, "P007", "A", 100));
//      topList.push(new Product(100, "P008", "A", 30));
//      topList.push(new Product(100, "P009", "A", 70));
//      topList.push(new Product(100, "P010", "A", 10));
      
      topList.push(new Product(100, "P001", "A", 10));
      topList.push(new Product(100, "P002", "A", 20));
      topList.push(new Product(100, "P003", "A", 30));
      topList.push(new Product(100, "P004", "A", 40));
//      topList.push(new Product(100, "P005", "A", 50));
      topList.push(new Product(100, "P005", "A", 90));
//      topList.push(new Product(100, "P005", "A", 40));
//      topList.push(new Product(100, "P006", "A", 80));
      topList.push(new Product(100, "P007", "A", 100));
//      topList.push(new Product(100, "P008", "A", 30));
      topList.push(new Product(100, "P009", "A", 70));
//      topList.push(new Product(100, "P010", "A", 10));
      
      topList.showList();
    }
    
    static class TopNList {
        static final int TOP_N = 5;
        ArrayList<Product> mTopProduct = new ArrayList<Product>();
        int mMinPrice = Integer.MIN_VALUE;
        String mMinPriceProductId;
        
        public void push(Product product) {
            int totalSalePrice = product.mPrice * product.mSaleCnt;
            if (totalSalePrice > mMinPrice) {
                if (mTopProduct.size() >= TOP_N) {
                    for(int i = 0; i < mTopProduct.size(); i++) {
                        if (mTopProduct.get(i).mProductId.equals(mMinPriceProductId)) {
                            mTopProduct.remove(i);
                        }
                    }
                }
                mTopProduct.add(product);
                
                int minPrice = Integer.MAX_VALUE;
                int tPrice;
                for (int i = 0; i < mTopProduct.size(); i++) {
                    tPrice = mTopProduct.get(i).mPrice * mTopProduct.get(i).mSaleCnt;
                    if (tPrice < minPrice) {
                        minPrice = tPrice;
                        mMinPrice = minPrice;
                        mMinPriceProductId = mTopProduct.get(i).mProductId;
                    }
                }
            }
        }
        
        public void showList() {
            for(int i = 0; i < mTopProduct.size(); i++) {
                System.out.println(mTopProduct.get(i).toString());
            }
        }
        
        public String getProductId() {
            StringBuffer ret = new StringBuffer();
            for(int i = 0; i < mTopProduct.size(); i++) {
                ret.append(mTopProduct.get(i).getProductId());
                ret.append(" ");
            }
            return ret.toString();
        }
        
//        @Override
//        public String toString() {
//            StringBuffer ret = new StringBuffer();
//            for(int i = 0; i < mTopProduct.size(); i++) {
//                ret.append(mTopProduct.get(i).toString());
//                ret.append(" ");
//            }
//            return ret.toString();
//        }
    }
    
    static class SmallCategory {
        public String mCateId;
        public String mCateName;
        
        public SmallCategory(String cateId, String cateName) {
            mCateId = cateId;
            mCateName = cateName;
        }
    }
    
    static class BigCategory {
        public String mCateId;
        public String mCateName;
        public String[] mSubCategories;
        
        public BigCategory(String cateId, String cateName, String[] subCate) {
            mCateId = cateId;
            mCateName = cateName;
            mSubCategories = subCate;
        }
    }
    
    static class Product {
        public int mPrice;
        public String mProductId;
        public String mSmallCategoryId;
        public int mSaleCnt;
        
        public Product(int price, String productId, String smallCateId, int saleCnt) {
            mPrice = price;
            mProductId = productId;
            mSmallCategoryId = smallCateId;
            mSaleCnt = saleCnt;
        }
        
        public String getProductId() {
            return mProductId;
        }
        
//        @Override
//        public String toString() {
//            return "price = " + mPrice + ", productId = " + mProductId + ", smallCateid = " + mSmallCategoryId + ", saleCnt = " + mSaleCnt;
//        }
    }
}
