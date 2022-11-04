package project;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import project.model.user.UserDBParser;
import project.model.user.container.User;

public class UserDBParserTest {
    private static String dbDir = UserDBParserTest.class.getResource("/user_db.json").getPath();
    private static List<User> fixture;

    @BeforeAll
    public static void setUp() {
        fixture = UserDBParser.loadDB(dbDir);
    }

    @AfterAll
    public static void tearDown() {
        fixture = null;
    }

    @Test
    public void testForNull() {
        assertNotNull(fixture);
    }

    @Test
    public void testNotEmpty() {
        assertEquals(fixture.isEmpty(), false);
    }

    @Test
    public void testCorrectSize() {
        assertEquals(fixture.size(), 6);
    }
    
    @Test
    public void testCorrectlyParsedUsernames() {
        List<String> expected = Arrays.asList(
            "charles123",
            "sergio123",
            "ruth123",
            "vincent123",
            "kasey123",
            "donald123"
        );
        List<String> actual = fixture.stream().map(User::username).collect(Collectors.toList());
        Collections.sort(expected);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testCorrectlyParsedPasswords() {
        List<String> expected = Arrays.asList(
            "abc40691",
            "abc42689",
            "abc55134",
            "abc59141",
            "abc60146",
            "abc23858"
        );
        List<String> actual = fixture.stream().map(User::password).collect(Collectors.toList());
        Collections.sort(expected);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }
}
