package com.trip.mbti;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import com.trip.mbti.rest.common.SearchDto;
import com.trip.mbti.rest.trip.TripEntity;
import com.trip.mbti.rest.trip.TripRequestDto;
import com.trip.mbti.rest.trip.TripService;


@SpringBootTest
@ActiveProfiles("test") 
class MbtiApplicationTests {
	
	@Autowired
	TripService tripService;
	
	@Test
	public void insertTest(){
		TripRequestDto tripRequestDto = new TripRequestDto();
		tripRequestDto.setMbtia("E");
		tripRequestDto.setMbtib("N");
		tripRequestDto.setMbtic("F");
		tripRequestDto.setMbtid("P");
		tripRequestDto.setTripNm("테스트 제목");
		tripRequestDto.setTripCts("테스트 내용");
		tripRequestDto.setRegUserId("testUser");
		

		TripEntity tripEntity = tripRequestDto.toEntity();
		tripService.save(tripEntity);

		assertTrue(true);
	}

	@Test
	public void test() {
		SearchDto searchDto = new SearchDto();
		searchDto.setSrchMbtia("E");
		searchDto.setSrchMbtib("N");
		searchDto.setSrchMbtic("F");
		searchDto.setSrchMbtid("P");
		
		TripEntity tripEntity = searchDto.toEntity();
		//TripEntity tripEntity = new TripEntity();
		// tripEntity.setMbtia("E");
		// tripEntity.setMbtib("N");
		// tripEntity.setMbtic("F");
		// tripEntity.setMbtid("P");
		Page<TripEntity> tripPage = tripService.findSearchTripMbtiPage(tripEntity, 1, 10);
		System.out.println("testTotalCnt : " + tripPage.getContent());
		assertTrue(tripEntity.getMbtia().equals(tripPage.getContent().get(0).getMbtia()));
		assertTrue(tripEntity.getMbtib().equals(tripPage.getContent().get(0).getMbtib()));
		assertTrue(tripEntity.getMbtic().equals(tripPage.getContent().get(0).getMbtic()));
		assertTrue(tripEntity.getMbtid().equals(tripPage.getContent().get(0).getMbtid()));
		
	}

	@Test
	public void selectTest(){
		List<TripEntity> list = tripService.findAllTripEntity();
		int cnt = 0;
		for(TripEntity trip : list){
			System.out.println("=====================");
			System.out.println(trip.get_Id());
			System.out.println(trip.getTripNm());
			System.out.println(trip.getTripCts());
			cnt++;
		}
		
		assertTrue("cnt 비교 ", cnt == list.size());
	}

	@Test
	public void deleteTest(){
		String testid = "6273bd24271c5e4b63b33bde";
		Optional<TripEntity> tripEntity = tripService.findOneById(testid);
		tripService.delete(tripEntity.get());
		
		if(tripService.findOneById(testid) == null){
			assertTrue(true);
		} else {
			System.out.println(tripService.findOneById(testid));

		}

		
	}
}
