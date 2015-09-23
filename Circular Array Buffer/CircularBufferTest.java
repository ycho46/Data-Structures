import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class CircularBufferTest {
    CircularBuffer<String> buff;
    @Before
    public void setUp() throws Exception {
        buff = new CircularArrayBuffer<String>();
    }

    @Test
    public void testIsEmptyAtFirst() {
        assertTrue("Initial buffer not empty", buff.isEmpty());
    
    }
    
    @Test
    public void testIsEmptyAfterAdd() {
        buff.add("A");
        assertTrue("Thinks its empty after an add", !buff.isEmpty());
    }
    
    @Test
    public void testIsEmptyAfterLastElementDeleted() {
        buff.add("A");
        buff.remove();
        assertTrue("Thinks it has something after remove of last item", buff.isEmpty());    
    }

    @Test
    public void testInitialCapacity() {
        assertEquals("Initial Capacity not correct", 10, buff.capacity());      
    }
    
    @Test
    public void testCapacityAfterRegrow() {
        addItems(14);
        assertEquals("Capacity after regrow incorrect", 20, buff.capacity());
    }
    
    @Test
    public void testCapacityAfterOverwrite() {
        buff.setMode(BufferGrowMode.OVERWRITE);
        addItems(14);
        assertEquals("Capacity after regrow incorrect", 10, buff.capacity());
    }
    
    private void addItems(int count) {
        for (int i = 0; i < count; i++) {
            buff.add("A" + i);
        }
    }

    @Test
    public void testInitialSize() {
        assertEquals("Initial Size not correct", 0, buff.size());  
    }
    
    @Test
    public void testSizeAfterOneInsert() {
        buff.add("C");
        assertEquals("Size after one addition wrong", 1, buff.size());
    }
    
    @Test
    public void testSizeAfterRemoveLastOne() {
        buff.add("C");
        buff.remove();
        assertEquals("Size after removing last item wrong", 0, buff.size());
    }
    
    @Test 
    public void testSizeAfterRemoveMultiple() {
        addItems(14);
        buff.remove();
        buff.remove();
        buff.remove();
        buff.remove();
        buff.remove();
        assertEquals("Size after removing multiple things wrong", 9, buff.size());
        
    }
    
    @Test
    public void testSizeWithRegrow() {
        addItems(15);
        assertEquals("Size after regrow wrong", 15, buff.size());   
    }
    
    @Test
    public void testSizeWithOverwrite() {
        buff.setMode(BufferGrowMode.OVERWRITE);
        addItems(15);
        assertEquals("Size with overwrite wrong", 10, buff.size());
    }

    @Test
    public void testAddOneItem() {
       buff.add("C");
       assertTrue("Actually added the item", buff.contains("C"));        
    }
    
    @Test
    public void testAddMultipleItemsRegrow() {
        addItems(15);
        assertTrue("Missing item after added into regrown container", buff.contains("A14"));
        assertTrue("Missing item after added into regrown container", buff.contains("A10"));
        assertTrue("Missing item afer added into regrown container", buff.contains("A9"));
        assertTrue("Missing item after added into regrown container", buff.contains("A0"));       
        assertEquals("Didn't add all items", 15, buff.size());
        assertEquals("Didn't reset head", "A0", buff.remove());
    }
    
    @Test
    public void testAddMultipleItemsOverwrite() {
        buff.setMode(BufferGrowMode.OVERWRITE);
        addItems(15);
        assertTrue("Missing item after added into regrown container", buff.contains("A14"));
        assertTrue("Missing item after added into regrown container", buff.contains("A5"));       
        assertEquals("Didn't add all items", 10, buff.size());
        assertEquals("Didn't set head correctly", "A5", buff.remove());
    }
    
    @Test
    public void testRegrowWithWrap() {
        addItems(9);
        buff.remove();
        buff.remove();
        buff.remove();
        buff.add("A10");
        buff.add("A11");
        buff.add("A12");
        buff.add("A13");
        buff.add("A14");
        assertFalse("Item should not be present", buff.contains("A0"));
        assertFalse("Item should not be present", buff.contains("A2"));
        assertTrue("Item not found", buff.contains("A3"));
        assertTrue("Item not found", buff.contains("A14"));     
    }
    

    @Test
    public void testRemoveOne() {
        buff.add("C");
        assertEquals("Did not get back the one item in buffer", "C", buff.remove());
    }
    
    @Test
    public void testRemoveWhenFull() {
        addItems(9);
        assertEquals("Remove from full buffer wrong", "A0", buff.remove());
        assertEquals("Remove from full buffer wrong", "A1", buff.remove());
    }
  
    @Test
    public void testRemoveWhenEmpty() {
        assertNull("Remove when empty did not return null", buff.remove());
    }
    
    @Test
    public void testRemoveWrappedAfterRegrow() {
        addItems(10);
        buff.remove();
        buff.remove();
        buff.remove();
        buff.add("A99");
        buff.add("A100");
        buff.add("A101");
        buff.add("A102");
        buff.add("A103");
        assertEquals("Remove from wrapped after regrow wrong", "A3", buff.remove());
    }


}
