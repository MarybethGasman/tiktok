package org.tm.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.tm.pojo.Relation;
import org.tm.pojo.User;
import org.tm.repository.RelationRepository;
import org.tm.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RelationService {

    private final RelationRepository relationRepository;

    private final UserRepository userRepository;

    public RelationService(RelationRepository relationRepository, UserRepository userRepository) {
        this.relationRepository = relationRepository;
        this.userRepository = userRepository;
    }

    public Optional<Boolean> checkIfAIsFollowB(Long userAId, Long userBId) {
        return Optional.ofNullable(relationRepository.selectFollowStatus(userAId, userBId));
    }

    public List<User> QueryFollowList(Long userId, Long viewerId) {

        List<Long> followingUserIdList =
                Optional.ofNullable(relationRepository.getFollowingUserIdList(viewerId))
                        .orElse(new ArrayList<>());

        List<User> userList = userRepository
                .selectUserListByUserIdList(followingUserIdList);

        if(viewerId != null) {
            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(viewerId))
                            .orElse(new ArrayList<>());

            userList = userList.stream().map((user -> {
                user.setIsFollow(followingIdList.contains(user.getUserId()));
                return user;
            })).collect(Collectors.toList());
        }
        return userList;
    }

    public List<User> QueryFollowerList(Long userId, Long viewerId) {
        List<Long> followerUserIdList =
                relationRepository.getFollowerUserIdList(userId);

        List<User> userList = userRepository
                .selectUserListByUserIdList(followerUserIdList);

        if(viewerId != null) {
            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(viewerId))
                            .orElse(new ArrayList<>());

            userList = userList.stream().map((user -> {
                user.setIsFollow(followingIdList.contains(user.getUserId()));
                return user;
            })).collect(Collectors.toList());
        }
        return userList;
    }

    public void addRelation(Long userId, Long toUserId) {

        Relation relation = new Relation();
        relation.setFollowerId(userId);
        relation.setFolloweeId(toUserId);

        try {
            relationRepository.insert(relation);
        }catch (DuplicateKeyException e) {
            relationRepository.updateIsDeleted(relation,false);
        }
        //增加关注数
        userRepository.addFollowCount(userId, 1);
        //增加粉丝数
        userRepository.addFollowerCount(toUserId, 1);
    }

    public void removeRelation(Long userId, Long toUserId) {
        Relation relation = new Relation();
        relation.setFollowerId(userId);
        relation.setFolloweeId(toUserId);

        relationRepository.updateIsDeleted(relation,true);
        //减少关注数
        userRepository.addFollowCount(userId, -1);

        //减少粉丝数
        userRepository.addFollowerCount(toUserId, -1);
    }
}
