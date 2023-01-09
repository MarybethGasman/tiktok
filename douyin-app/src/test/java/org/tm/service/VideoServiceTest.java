package org.tm.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tm.dto.VideoDTO;
import org.tm.repository.FavoriteRepository;
import org.tm.repository.RelationRepository;
import org.tm.repository.VideoRepository;

import java.util.List;

class VideoServiceTest {

    @Mock private VideoRepository videoRepository;
    @Mock private FavoriteRepository favoriteRepository;
    @Mock private RelationRepository relationRepository;

    private VideoService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new VideoService(videoRepository, favoriteRepository, relationRepository);
    }

    @Test
    void getAllVideoList() {

        String lastTime = "2022 06-21";

        List<VideoDTO> videoList = List.of(
                new VideoDTO(),
                new VideoDTO(),
                new VideoDTO(),
                new VideoDTO(),
                new VideoDTO(),
                new VideoDTO()
        );

        //given
        //BDDMockito.given(videoRepository.GetListSortedByCreateTime(lastTime))
                //.willReturn(videoList);
        //when
        //List<VideoDTO> actual = underTest.getAllVideoList(lastTime, 2L);

        //then
        //Assertions.assertThat(actual)
        //        .isEqualTo(videoList);
    }
}