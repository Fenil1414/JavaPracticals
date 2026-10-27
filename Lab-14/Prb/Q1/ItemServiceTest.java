public class ItemServiceTest {

    @Test
    void addingItemIncreasesCount() {
        ItemService service = new ItemService();

        assertEquals(0, service.count());

        service.addItem(new Item("Pen"));

        assertEquals(1, service.count());
    }

    private void assertEquals(int i, int count) {
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    @Test
    void invalidActionThrowsException() {
        ItemService service = new ItemService();

        assertThrows(IllegalArgumentException.class, () -> {
            service.performAction("invalid");
        });
    }

    private void assertThrows1(Class<IllegalArgumentException> class1, Runnable object) {
        throw new UnsupportedOperationException("Unimplemented method 'assertThrows'");
    }

    private void assertThrows(Class<IllegalArgumentException> class1, Runnable object) {
        throw new UnsupportedOperationException("Unimplemented method 'assertThrows'");
    }

    @Test
    void addingNullThrowsException() {
        ItemService service = new ItemService();

        assertThrows1(IllegalArgumentException.class, () -> {
            service.addItem(null);
        });
    }
}