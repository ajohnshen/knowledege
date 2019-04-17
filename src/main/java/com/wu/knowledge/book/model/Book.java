package com.wu.knowledge.book.model;

import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.common.model.BaseModel;

/**
 * 〈功能简述〉<br>
 * 〈书籍实体类〉
 *
 * @create create by WSD on 2019/1/18
 */
public class Book extends BaseModel {
    /**
     * 人气
     */
    private Integer popularity;
    /**
     * 默认人气
     */
    private Integer def_popularity;
    /**
     * 作者
     */
    private String author;
    /**
     * 出版社
     */
    private String publisher;
    /**
     * 读后感
     */
    private String readresponse;
    /**
     * 书籍类型
     */
    private Dictionary booktype;
    /**
     * 书籍类型数组
     */
    private Integer[] booktypeIDs;

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getDef_popularity() {
        return def_popularity;
    }

    public void setDef_popularity(Integer def_popularity) {
        this.def_popularity = def_popularity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Dictionary getBooktype() {
        return booktype;
    }

    public void setBooktype(Dictionary booktype) {
        this.booktype = booktype;
    }

    public Integer[] getBooktypeIDs() {
        return booktypeIDs;
    }

    public void setBooktypeIDs(Integer[] booktypeIDs) {
        this.booktypeIDs = booktypeIDs;
    }

    public String getReadresponse() {
        return readresponse;
    }

    public void setReadresponse(String readresponse) {
        this.readresponse = readresponse;
    }
}
