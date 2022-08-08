package spring.bappy.controllers;


import com.google.api.gax.paging.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.bappy.controllers.response.Message;
import spring.bappy.controllers.response.PageInfo;
import spring.bappy.controllers.response.StatusEnum;
import spring.bappy.domain.DTO.HangoutDto;
import spring.bappy.domain.Hangout.HangoutInfo;
import spring.bappy.domain.Place;
import spring.bappy.service.HangoutService;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hangout")
public class HangoutController {

    private final HangoutService hangoutService;

    @Autowired
    public HangoutController(HangoutService hangoutService) {
        this.hangoutService = hangoutService;
    }

    @GetMapping("/list/{pageNum}")
    public ResponseEntity getHangoutList(@PathVariable int pageNum, HttpServletRequest request, ArrayList<String> hangoutCategory, String hangoutSort) {
        String userId = (String)request.getAttribute("userId");
        Map<String, Object> response = new HashMap<>();


        response.put("message", "hangout list");
        response.put("status", StatusEnum.OK);
        PageInfo i = new PageInfo(0, 10);
        Map<String, Object> info = new HashMap<>();
        info.put("pageInfo",i);
        info.put("HangoutList", hangoutService.getHangoutDtoList(pageNum, userId));

        response.put("data", info);




        return new ResponseEntity(response, HttpStatus.OK);

    }



    @GetMapping("/{hangoutInfoId}")
    public ResponseEntity getHangoutDetail(@PathVariable String hangoutInfoId,HttpServletRequest request) {
        String userId = (String)request.getAttribute("userId");
        HangoutDto hangoutDto = hangoutService.getHangoutDetail(hangoutInfoId,userId);
        Message message = new Message();
        message.setMessage("hangout info");
        message.setData(hangoutDto);
        message.setStatus(StatusEnum.OK);
        System.out.println(hangoutDto);
        return new ResponseEntity(message, HttpStatus.OK);

    }

    @PostMapping("")
    public ResponseEntity createHangout(HangoutInfo hangoutInfo, Place place, HttpServletRequest request, @RequestPart MultipartFile file) {
        hangoutInfo.setHangoutPlace(place);
        String userId = (String)request.getAttribute("userId");
        System.out.println(hangoutInfo.getHangoutCategory());

        hangoutService.createHangout(hangoutInfo, userId,file);
        Message message = new Message();
        message.setMessage("create hangout success");
        message.setData(true);
        message.setStatus(StatusEnum.OK);

        return new ResponseEntity(message,HttpStatus.OK);
    }

    /*
        행아웃 참여, 취소
     */
    @PutMapping("/{hangoutInfoId}")
    public ResponseEntity joinHangout(@PathVariable String hangoutInfoId, HttpServletRequest request, @RequestParam String action) {

        String userId = (String) request.getAttribute("userId");

        System.out.println("it is "+action);
        if(action.equals("join")) {
            hangoutService.joinHangout(hangoutInfoId, userId);
        }else {
            hangoutService.cancelHangout(hangoutInfoId, userId);
        }
        Message message = new Message();
        message.setMessage("hangout "+ action + "success");
        message.setData(true);
        message.setStatus(StatusEnum.OK);

        return new ResponseEntity<>(message,HttpStatus.ACCEPTED);

    }


    @PutMapping("/like/{hangoutInfoId}")
    public ResponseEntity likeHangout(@PathVariable String hangoutInfoId, HttpServletRequest request) {
        String userId = (String)request.getAttribute("userId");
        System.out.println(hangoutInfoId);

        hangoutService.likeHangout(hangoutInfoId, userId);
        Message message = new Message();
        message.setMessage("like");
        message.setData(true);
        message.setStatus(StatusEnum.OK);

        return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
    }

    @PutMapping("/noLike/{hangoutInfoId}")
    public ResponseEntity noLikeHangout(@PathVariable String hangoutInfoId, HttpServletRequest request) {
        String userId = (String)request.getAttribute("userId");
        hangoutService.noLikeHangout(hangoutInfoId, userId);
        Message message = new Message();
        message.setMessage("no like");
        message.setData(true);
        message.setStatus(StatusEnum.OK);
        return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
    }



}
