package com.wu.knowledge.fileextend.model;


import com.wu.knowledge.basedata.dictionary.model.Dictionary;
import com.wu.knowledge.common.model.BaseModel;

/**
 * 〈功能简述〉<br>
 * 〈文件实体〉
 *
 * @create Created by WSD on 2019/3/20.
 * @since 1.0.0
 */
public class File1 extends BaseModel {
    private static final long serialVersionUID = -7458944224636480333L;
    /**
     * 文件名
     */
    private String file_name;

    /**
     * 文件加密的名
     */
    private String file_nameMD5;

    /**
     * 文件类型
     */
    private Dictionary file_type;

    /**
     * 文件大小(单位:)
     */
    private Integer file_size;

    /**
     * 文件路径
     */
    private String file_path;

    /**
     * 文件后缀
     */
    private String file_suffix;

    /**
     * 是否是视频
     */
    private Boolean video;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public Dictionary getFile_type() {
        return file_type;
    }

    public void setFile_type(Dictionary file_type) {
        this.file_type = file_type;
    }

    public Integer getFile_size() {
        return file_size;
    }

    public void setFile_size(Integer file_size) {
        this.file_size = file_size;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_suffix() {
        return file_suffix;
    }

    public void setFile_suffix(String file_suffix) {
        this.file_suffix = file_suffix;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getFile_nameMD5() {
        return file_nameMD5;
    }

    public void setFile_nameMD5(String file_nameMD5) {
        this.file_nameMD5 = file_nameMD5;
    }
}
