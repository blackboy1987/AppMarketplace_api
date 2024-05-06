package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author black
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category extends OrderedEntity<Long> {
    /**
     * 树路径分隔符
     */
    public static final String TREE_PATH_SEPARATOR = "|";
    @JsonView({PageView.class})
    private String name;

    @Column(unique = true,nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    /**
     * 树路径
     */
    @Column(nullable = false)
    private String treePath;

    /**
     * 层级
     */
    @Column(nullable = false)
    private Integer grade;

    /**
     * 全称
     */
    @Column(nullable = false, length = 4000)
    private String fullName;

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY)
    private Set<Category> children = new HashSet<>();

    @ManyToMany(mappedBy = "categories",fetch = FetchType.LAZY)
    private Set<Soft> softs = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public Set<Soft> getSofts() {
        return softs;
    }

    public void setSofts(Set<Soft> softs) {
        this.softs = softs;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    /**
     * 获取所有上级地区ID
     *
     * @return 所有上级地区ID
     */
    @Transient
    public Long[] getParentIds() {
        String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
        Long[] result = new Long[parentIds.length];
        for (int i = 0; i < parentIds.length; i++) {
            result[i] = Long.valueOf(parentIds[i]);
        }
        return result;
    }

    /**
     * 获取所有上级地区
     *
     * @return 所有上级地区
     */
    @Transient
    public List<Category> getParents() {
        List<Category> parents = new ArrayList<>();
        recursiveParents(parents, this);
        return parents;
    }

    /**
     * 递归上级地区
     *
     * @param parents
     *            上级地区
     * @param area
     *            地区
     */
    private void recursiveParents(List<Category> parents, Category area) {
        if (area == null) {
            return;
        }
        Category parent = area.getParent();
        if (parent != null) {
            parents.add(0, parent);
            recursiveParents(parents, parent);
        }
    }
}
