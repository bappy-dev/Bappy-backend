package spring.bappy.service;


import com.google.api.gax.paging.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.bappy.domain.DTO.HangoutDto;
import spring.bappy.domain.Hangout.HangoutInfo;
import spring.bappy.domain.User.UserInfo;
import spring.bappy.repository.HangoutInfoRepository;
import spring.bappy.repository.UserInfoRepository;
import spring.bappy.repository.UserStatRepository;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HangoutService {

    private final HangoutInfoRepository hangoutInfoRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserStatRepository userStatRepository;

    private final UserService userService;

//    public List<HangoutInfo> getHangoutListByPage(int pageNum) {
//        hangoutInfoRepository.findAll()
//    }



    public List<HangoutDto> getHangoutDtoList(int pageNum, String userId) {
        List<HangoutInfo> hangoutInfoList = hangoutInfoRepository.findBy(PageRequest.of(pageNum*10, 10));
        List<HangoutDto> hangoutDtoList = new ArrayList<HangoutDto>();
        for(HangoutInfo info : hangoutInfoList) {
            hangoutDtoList.add(getHangoutDto(info,userId));
        }
        return hangoutDtoList;
    }

    public ArrayList<HangoutDto> getHangoutDtoListById(ArrayList<String> hangoutInfoId, String userId) {
        ArrayList<HangoutDto> hangoutDtoList = new ArrayList<HangoutDto>();
        for(String id : hangoutInfoId) {
            hangoutDtoList.add(getHangoutDetail(id,userId));
        }
        return hangoutDtoList;
    }
    public HangoutDto getHangoutDto(HangoutInfo hangoutInfo, String userId) {
        HangoutDto hangoutDto = new HangoutDto(userService);
        //HangoutInfo hangoutInfo = hangoutInfoRepository.findHangoutInfoByHangoutInfoId(hangoutInfoId);
        hangoutDto.SetHangoutDto(hangoutInfo,userId);
        return hangoutDto;
    }

    public HangoutDto getHangoutDetail(String hangoutInfoId, String userId) {
        HangoutDto hangoutDto = new HangoutDto(userService);
        HangoutInfo hangoutInfo = hangoutInfoRepository.findHangoutInfoByHangoutInfoId(hangoutInfoId);
        hangoutDto.SetHangoutDto(hangoutInfo,userId);
        return hangoutDto;
    }

    public boolean likeHangout(String hangoutInfoId, String userId) {
        HangoutInfo hangoutInfo = hangoutInfoRepository.findHangoutInfoByHangoutInfoId(hangoutInfoId);
        hangoutInfo.addHangoutLikeList(userId);
        userService.addUserLikeList(userId,hangoutInfoId);
        hangoutInfoRepository.save(hangoutInfo);
        return true;
    }

    public boolean noLikeHangout(String hangoutInfoId, String userId) {

        HangoutInfo hangoutInfo = hangoutInfoRepository.findHangoutInfoByHangoutInfoId(hangoutInfoId);
        hangoutInfo.removeHangoutLikeList(userId);
        hangoutInfoRepository.save(hangoutInfo);
        userService.removeUserLikeList(userId,hangoutInfoId);
        return true;
    }

    public boolean joinHangout(String hangoutInfoId, String userId) {
        HangoutInfo hangoutInfo = hangoutInfoRepository.findHangoutInfoByHangoutInfoId(hangoutInfoId);
        hangoutInfo.addHangoutUserList(userId);
        hangoutInfoRepository.save(hangoutInfo);
        userService.addUserJoinList(userId,hangoutInfoId);
        return true;
    }

    public boolean cancelHangout(String hangoutInfoId, String userId) {
        HangoutInfo hangoutInfo = hangoutInfoRepository.findHangoutInfoByHangoutInfoId(hangoutInfoId);
        hangoutInfo.removeHangoutUserList(userId);
        hangoutInfoRepository.save(hangoutInfo);
        userService.removeUserJoinList(userId,hangoutInfoId);
        return true;
    }

    public void createHangout(HangoutInfo hangoutInfo, String userId, MultipartFile file) {
        hangoutInfo.setHangoutImageUrl(RandomStringUtils.randomAlphabetic(10) + file.getOriginalFilename());
        System.out.println(hangoutInfo.getHangoutImageUrl());

        try {
            URL resourceUrl = this.getClass().getResource("/");
            String RESOURCES_DIR = this.getClass().getClassLoader().getResource("static/").getPath();
            //String RESOURCES_DIR = this.getClass().getClassLoader().getResource("static").getPath();
            System.out.println(RESOURCES_DIR);
            Path newFile = Paths.get(RESOURCES_DIR + hangoutInfo.getHangoutImageUrl());

            Files.createDirectories(newFile.getParent());
            Files.write(newFile, file.getBytes());
//
//            System.out.println(str);
//            File saveFile = new File(str);
//            //Path path = Paths.get(str).toRealPath();
//           // System.out.println(path);
//            file.transferTo(saveFile);

        } catch (IllegalStateException e) {
            e.printStackTrace();
            System.out.println("error");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
            return;
        }


        hangoutInfo.setHangoutCurrentNum(0);
        hangoutInfo.setHangoutVisitCount(0);
        hangoutInfo.setHangoutLikeCount(0);
        hangoutInfo.addHangoutUserList(userId);

        String hangoutInfoId = hangoutInfoRepository.save(hangoutInfo).getHangoutInfoId().toString();
        userService.addUserJoinList(userId,hangoutInfoId);
        userService.addUserMadeList(userId,hangoutInfoId);

    //    System.out.println(hangoutInfoRepository.save(hangoutInfo));


    }
}
