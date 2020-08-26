package ladder.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.stream.Collectors;

public class LadderTest {
    private static final LineGenerator LINE_RANDOM_GENERATOR = new LineRandomGenerator();
    private static final LineGenerator LINE_REPEAT_GENERATOR = new LineRepeatGenerator();

    @DisplayName("사다리 전체(Lines) 생성 테스트")
    @ParameterizedTest
    @CsvSource(value = {"tt,lala:4", "dd,momo,coco:5", "nana,toto,dodo,coco,lulu:10"}, delimiter = ':')
    void Lines_of(String participant, int ladderHeight) {
        Ladder ladder = Ladder.of(ladderHeight, Participants.of(participant), LINE_REPEAT_GENERATOR);

        ladder.getLines()
                .stream()
                .map(Line::getConnection)
                .map(this::makeStringForView)
                .forEach(System.out::println);

        System.out.println();
    }

    private String makeStringForView(List<Boolean> connection) {
        return connection.stream()
                .map(bol -> bol ? "-----" : "     ")
                .map(line -> "|" + line)
                .collect(Collectors.joining());
    }
}
