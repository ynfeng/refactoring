package chapter4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author fynwin@gmail.com
 */
public class ProvinceTest {
    
    @Test
    public void should_shortfall(){
        Province province = new Province(Province.sampleProvinceData());
        assertThat(province.shortfall(), is(5));
    }

}
