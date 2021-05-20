package sk.martialhero.utils;

import java.util.ArrayList;

public interface Constants {
    int RC_SIGN_IN = 7;
    String USERS_REF = "usersRef";
    String GROUPS_REF = "groupsRef";
    String USER = "user";

    //Collections
    String USER_COLLECTION = "users";
    String GROUP_COLLECTION = "groups";

    //User fields
    String KEY_UID = "uid";
    String KEY_FIRST_NAME = "first_name";
    String KEY_LAST_NAME = "last_name";
    String KEY_EMAIL = "email";

    //Group fields
    String KEY_GROUP_UID = "group_uid";
    String KEY_GROUP_NAME = "group_name";
    String KEY_GROUP_IMAGE = "group_image";
    String KEY_GROUP_ADMIN = "group_admin";
    String KEY_GROUP_LOCATION = "group_location";
    String KEY_GROUP_DESCRIPTION = "group_description";
}
