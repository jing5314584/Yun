package com.yun.mybatis.model;

import java.util.Date;

import com.yun.util.DateUtil;

public class V_GroupOrder {
    private String id;

    private Integer goodsId;

    private String userId;

    private Integer price;

    private Date createTime;

    private String goodsName;

    private String goodsImg;

    private Integer payType;

    private String parent;

    private Date payTime;

    private Integer type;

    private Boolean isComment;

    private Integer addressId;

    private String nickName;

    private String userIcon;

    private String url;

    private Integer catId;

    private Integer goodsNumber;

    private Integer groupNum;

    private Integer marketPrice;

    private Integer singlePrice;

    private Integer groupPrice;

    private Date groupStartDate;

    private Date groupEndDate;

    private String keywords;

    private String goodsBrief;

    private String goodsThumb;

    private Boolean isReal;

    private Boolean isOnSale;

    private Integer sortOrder;

    private Boolean isDelete;

    private Integer goodsType;

    private Date lastUpdateTime;

    private String consignee;

    private String province;

    private String city;

    private String district;

    private String address;

    private String mobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg == null ? null : goodsImg.trim();
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent == null ? null : parent.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getIsComment() {
        return isComment;
    }

    public void setIsComment(Boolean isComment) {
        this.isComment = isComment;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(Integer singlePrice) {
        this.singlePrice = singlePrice;
    }

    public Integer getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(Integer groupPrice) {
        this.groupPrice = groupPrice;
    }

    public Date getGroupStartDate() {
        return groupStartDate;
    }

    public void setGroupStartDate(Date groupStartDate) {
        this.groupStartDate = groupStartDate;
    }

    public Date getGroupEndDate() {
        return groupEndDate;
    }

    public void setGroupEndDate(Date groupEndDate) {
        this.groupEndDate = groupEndDate;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief == null ? null : goodsBrief.trim();
    }

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb == null ? null : goodsThumb.trim();
    }

    public Boolean getIsReal() {
        return isReal;
    }

    public void setIsReal(Boolean isReal) {
        this.isReal = isReal;
    }

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", userId=").append(userId);
        sb.append(", price=").append(price);
        sb.append(", createTime=").append(createTime);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsImg=").append(goodsImg);
        sb.append(", payType=").append(payType);
        sb.append(", parent=").append(parent);
        sb.append(", payTime=").append(payTime);
        sb.append(", type=").append(type);
        sb.append(", isComment=").append(isComment);
        sb.append(", addressId=").append(addressId);
        sb.append(", nickName=").append(nickName);
        sb.append(", userIcon=").append(userIcon);
        sb.append(", url=").append(url);
        sb.append(", catId=").append(catId);
        sb.append(", goodsNumber=").append(goodsNumber);
        sb.append(", groupNum=").append(groupNum);
        sb.append(", marketPrice=").append(marketPrice);
        sb.append(", singlePrice=").append(singlePrice);
        sb.append(", groupPrice=").append(groupPrice);
        sb.append(", groupStartDate=").append(groupStartDate);
        sb.append(", groupEndDate=").append(groupEndDate);
        sb.append(", keywords=").append(keywords);
        sb.append(", goodsBrief=").append(goodsBrief);
        sb.append(", goodsThumb=").append(goodsThumb);
        sb.append(", isReal=").append(isReal);
        sb.append(", isOnSale=").append(isOnSale);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", goodsType=").append(goodsType);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", consignee=").append(consignee);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", district=").append(district);
        sb.append(", address=").append(address);
        sb.append(", mobile=").append(mobile);
        sb.append("]");
        return sb.toString();
    }
    public String getCreateTimeFormat(){
    	if(createTime != null){
    		return DateUtil.format(createTime, "yyyy.MM.dd");
    	}
    	return "";
    }
}