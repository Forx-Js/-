package com.ht.bim.constant;

import org.apache.commons.lang.ArrayUtils;

/**
 * Created by zhong on 2019-1-31.
 */
public class RoleTypes {
    public static final int SYS_ROLE = 1;
    public static final int PROJECT_ROLE = 2;

    private static final int[] roles = new int[]{SYS_ROLE, PROJECT_ROLE};

    public static boolean isEffectRoleType(int roleType) {
        return ArrayUtils.contains(roles, roleType);
    }
}
