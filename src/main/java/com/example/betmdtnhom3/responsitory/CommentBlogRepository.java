package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.CommentBlog;
import com.example.betmdtnhom3.entity.User;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface CommentBlogRepository extends JpaRepository<CommentBlog, Integer> {
    List<CommentBlog> findById(SingularAttribute<AbstractPersistable, Serializable> blogId);

}
