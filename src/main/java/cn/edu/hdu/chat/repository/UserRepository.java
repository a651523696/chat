package cn.edu.hdu.chat.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.edu.hdu.chat.model.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	@Override
	Page<User> findAll(Pageable pageable);

	@Override
	<S extends User> S save(S entity);

	@Override
	User findOne(Long id);

	@Override
	boolean exists(Long id);

	@Override
	long count();

	@Override
	void delete(Long id);

	@Override
	void delete(User entity);

	@Override
	void delete(Iterable<? extends User> entities);

	@Override
	void deleteAll();

	@Override
	List<User> findAll();

	@Override
	List<User> findAll(Sort sort);

	@Override
	List<User> findAll(Iterable<Long> ids);

	@Override
	<S extends User> List<S> save(Iterable<S> entities);

	@Override
	void flush();

	@Override
	<S extends User> S saveAndFlush(S entity);

	 User findByUsername(String username);
	
}
