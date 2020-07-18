package p4_group_8_repo.initialize;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class Write_view_scoreTest {

    private int[] data = {1,2,3,4,5};
    @Test
    int[] sort_score(int[] arrayList) {
        int[] data_reverse = sort_score(data);
        int[] reverse = {5,4,3,2,1};
        assertEquals(reverse, data_reverse);
        return data_reverse;
    }
    @Test
    int[] sort_score_should_not_equal(int[] arrayList) {
        int[] data_reverse = sort_score(data);
        int[] reverse = {5,4,3,1,2};
        assertNotEquals(reverse,data_reverse);
        return data_reverse;
    }
}