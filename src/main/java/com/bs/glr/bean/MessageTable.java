package com.bs.glr.bean;

import java.util.Date;

public class MessageTable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_table.id
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_table.uname
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    private String uname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_table.real_name
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    private String realName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_table.message_content
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    private String messageContent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_table.message_time
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    private Date messageTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_table.message_reply
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    private String messageReply;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_table.message_status
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    private String messageStatus;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_table.id
     *
     * @return the value of message_table.id
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_table.id
     *
     * @param id the value for message_table.id
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_table.uname
     *
     * @return the value of message_table.uname
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public String getUname() {
        return uname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_table.uname
     *
     * @param uname the value for message_table.uname
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_table.real_name
     *
     * @return the value of message_table.real_name
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_table.real_name
     *
     * @param realName the value for message_table.real_name
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_table.message_content
     *
     * @return the value of message_table.message_content
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_table.message_content
     *
     * @param messageContent the value for message_table.message_content
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_table.message_time
     *
     * @return the value of message_table.message_time
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public Date getMessageTime() {
        return messageTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_table.message_time
     *
     * @param messageTime the value for message_table.message_time
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_table.message_reply
     *
     * @return the value of message_table.message_reply
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public String getMessageReply() {
        return messageReply;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_table.message_reply
     *
     * @param messageReply the value for message_table.message_reply
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public void setMessageReply(String messageReply) {
        this.messageReply = messageReply == null ? null : messageReply.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_table.message_status
     *
     * @return the value of message_table.message_status
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public String getMessageStatus() {
        return messageStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_table.message_status
     *
     * @param messageStatus the value for message_table.message_status
     *
     * @mbg.generated Wed Jan 31 22:01:47 CST 2024
     */
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus == null ? null : messageStatus.trim();
    }
}