package org.androsovich.applications.dto.assemblers;

import lombok.extern.slf4j.Slf4j;
import org.androsovich.applications.dto.bid.BidResponse;
import org.androsovich.applications.entities.Bid;
import org.androsovich.applications.entities.User;
import org.androsovich.applications.entities.embeddeds.Phone;
import org.androsovich.applications.entities.enums.StatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class BidModelAssemblerTest {
    private BidModelAssembler bidModelAssembler;
    private List<Bid> bids;


    @BeforeEach
    public void init() {
        log.info("init BidModelAssemblerTest ");

        bidModelAssembler = new BidModelAssembler();

        bids = List.of(
                new Bid("test1", "text1", StatusType.REJECTED, new Phone(), LocalDateTime.now().minusHours(1),
                        User.builder()
                                .firstName("first1")
                                .lastName("last1")
                                .build()),
                new Bid("test2", "text2", StatusType.DRAFT, new Phone(), LocalDateTime.now().minusHours(2),
                        User.builder()
                                .firstName("first2")
                                .lastName("last2")
                                .build()),
                new Bid("test3", "text3", StatusType.SENT, new Phone(), LocalDateTime.now().plusHours(1),
                        User.builder()
                                .firstName("first3")
                                .lastName("last3")
                                .build())
        );
        LongStream.range(0, bids.size()).forEach(index->bids.get((int)index).setId(index));
    }

    @Test
    void sizeContentInCollectionModelEqualTestSize() {
        assertEquals(bidModelAssembler.toCollectionModel(bids).getContent().size(), bids.size());
    }

    @Test
    void testMethodToModel() {
        Bid bid = new Bid("test1", "text1", StatusType.REJECTED, new Phone(), LocalDateTime.now().minusHours(1), new User());
        bid.setId(1L);
        bid.getUser().setId(2L);
        bid.getUser().setFirstName("Mark");
        bid.getUser().setLastName("Rufus");
        BidResponse bidResponse =  bidModelAssembler.toModel(bid);
        assertEquals(bidResponse.getId(), bid.getId());
        assertEquals(bidResponse.getName(), bid.getName());
        assertEquals(bidResponse.getStatus(), bid.getStatus().name());
        assertEquals(bidResponse.getCreatedTime(), bid.getCreatedTime().toString());
        assertEquals(bidResponse.getFirstName(), bid.getUser().getFirstName());
        assertEquals(bidResponse.getLastName(), bid.getUser().getLastName());

    }
}