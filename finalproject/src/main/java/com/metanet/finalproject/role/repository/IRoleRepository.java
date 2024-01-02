package com.metanet.finalproject.role.repository;

import com.metanet.finalproject.role.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IRoleRepository {
    public String getRoleName(@Param("memberId") String memberId);

    public void deleteRole(@Param("memberId") String memberId);

    public void insertRole(@Param("role") Role role);
}
