package com.best.zcdn.mapper;

import com.best.zcdn.bean.QABean;
import com.best.zcdn.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int inserUserSysDialog(QABean bean);

    void delUserSysDialog (@Param(value = "qaId") Integer qaId);

    void delBranchSysDialog (List<Integer> list);

    void createTable();

    void delUserSysDialog1(int i);

    QABean getQAByFilter(Integer id);

    int updateQA(QABean bean);

    User queryUserDetail(Long userId);
    /**
     * 根据用户ID查询其角色列表
     * @param userId
     * @return
     */
    List<String> queryRoleIdByUserId(@Param("userId")Long userId);

    /**
     * 根据用户ID查询其岗位列表
     * @param userId
     * @return
     */
    List<Long> queryPostIdByUserId(@Param("userId")Long userId);

    /**
     * 根据用户ID查询其部门对接人的信息
     * @param userId
     * @return
     */
    User queryDeptUserByUserId(@Param("userId")Long userId);

    /**
     * 根据用户ID查询其部门的指定岗位人员
     * @param userId
     * @param postId
     * @return
     */
    List<User> queryDeptPostByUserIdPostId(@Param("userId")Long userId, @Param("postId")Long postId);
}
