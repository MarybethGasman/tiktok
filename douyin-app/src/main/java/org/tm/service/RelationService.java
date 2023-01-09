package org.tm.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.tm.dto.UserDTO;
import org.tm.po.FavoritePO;
import org.tm.po.RelationPO;
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

    public List<UserDTO> QueryFollowList(Long userId, Long viewerId) {

        List<Long> followingUserIdList =
                Optional.ofNullable(relationRepository.getFollowingUserIdList(viewerId))
                        .orElse(new ArrayList<>());

        List<UserDTO> userDTOList = userRepository
                .selectUserListByUserIdList(followingUserIdList);

        if(viewerId != null) {
            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(viewerId))
                            .orElse(new ArrayList<>());

            userDTOList = userDTOList.stream().map((userDTO -> {
                userDTO.setFollow(followingIdList.contains(userDTO.getUserId()));
                return userDTO;
            })).collect(Collectors.toList());
        }
        return userDTOList;
    }

    public List<UserDTO> QueryFollowerList(Long userId, Long viewerId) {
        List<Long> followerUserIdList =
                relationRepository.getFollowerUserIdList(userId);

        List<UserDTO> userDTOList = userRepository
                .selectUserListByUserIdList(followerUserIdList);

        if(viewerId != null) {
            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(viewerId))
                            .orElse(new ArrayList<>());

            userDTOList = userDTOList.stream().map((userDTO -> {
                userDTO.setFollow(followingIdList.contains(userDTO.getUserId()));
                return userDTO;
            })).collect(Collectors.toList());
        }
        return userDTOList;
    }

    public void addRelation(Long userId, Long toUserId) {

        RelationPO relationPO = new RelationPO();
        relationPO.setFollowerId(userId);
        relationPO.setFolloweeId(toUserId);

        try {
            relationRepository.insert(relationPO);
        }catch (DuplicateKeyException e) {
            relationRepository.updateIsDeleted(relationPO,false);
        }
        //增加关注数
        userRepository.addFollowCount(userId, 1);
        //增加粉丝数
        userRepository.addFollowerCount(toUserId, 1);
    }

    public void removeRelation(Long userId, Long toUserId) {
        RelationPO relationPO = new RelationPO();
        relationPO.setFollowerId(userId);
        relationPO.setFolloweeId(toUserId);

        relationRepository.updateIsDeleted(relationPO,true);
        //减少关注数
        userRepository.addFollowCount(userId, -1);

        //减少粉丝数
        userRepository.addFollowerCount(toUserId, -1);
    }
}
