package jsoft.projects.photoclick.vk.api;

public class SearchDialogItem {
    
    public enum SDIType { USER, CHAT, EMAIL }
    
    public String str_type;
    public SDIType type;
    public String email;
    public User user;
    public Message chat;
    
}
