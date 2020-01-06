package life.picacg.community.community.model;

public class Publish {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLISH.ID
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLISH.TITLE
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLISH.GMT_CREATE
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLISH.GMT_MODIFIED
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLISH.CREATOR
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    private Integer creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLISH.COMMENT_COUNT
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    private Integer commentCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLISH.VIEW_COUNT
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    private Integer viewCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLISH.LIKE_COUNT
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    private Integer likeCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLISH.TAG
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    private String tag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLISH.DESCRIPTION
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLISH.ID
     *
     * @return the value of PUBLISH.ID
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLISH.ID
     *
     * @param id the value for PUBLISH.ID
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLISH.TITLE
     *
     * @return the value of PUBLISH.TITLE
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLISH.TITLE
     *
     * @param title the value for PUBLISH.TITLE
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLISH.GMT_CREATE
     *
     * @return the value of PUBLISH.GMT_CREATE
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLISH.GMT_CREATE
     *
     * @param gmtCreate the value for PUBLISH.GMT_CREATE
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLISH.GMT_MODIFIED
     *
     * @return the value of PUBLISH.GMT_MODIFIED
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLISH.GMT_MODIFIED
     *
     * @param gmtModified the value for PUBLISH.GMT_MODIFIED
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLISH.CREATOR
     *
     * @return the value of PUBLISH.CREATOR
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLISH.CREATOR
     *
     * @param creator the value for PUBLISH.CREATOR
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLISH.COMMENT_COUNT
     *
     * @return the value of PUBLISH.COMMENT_COUNT
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLISH.COMMENT_COUNT
     *
     * @param commentCount the value for PUBLISH.COMMENT_COUNT
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLISH.VIEW_COUNT
     *
     * @return the value of PUBLISH.VIEW_COUNT
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLISH.VIEW_COUNT
     *
     * @param viewCount the value for PUBLISH.VIEW_COUNT
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLISH.LIKE_COUNT
     *
     * @return the value of PUBLISH.LIKE_COUNT
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLISH.LIKE_COUNT
     *
     * @param likeCount the value for PUBLISH.LIKE_COUNT
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLISH.TAG
     *
     * @return the value of PUBLISH.TAG
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLISH.TAG
     *
     * @param tag the value for PUBLISH.TAG
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLISH.DESCRIPTION
     *
     * @return the value of PUBLISH.DESCRIPTION
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLISH.DESCRIPTION
     *
     * @param description the value for PUBLISH.DESCRIPTION
     *
     * @mbg.generated Fri Jan 03 14:45:56 CST 2020
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}