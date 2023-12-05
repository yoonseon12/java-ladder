package nextstep.ladder.domain.lines;

import static nextstep.ladder.domain.Line.HORIZONTAL_LINE_OVERLAPPING_EXCEPTION;
import static nextstep.ladder.domain.Line.LINE_LENGTH_DIFFERENCE_EXCEPTION;
import static nextstep.ladder.domain.lines.Lines.LINES_EMPTY_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import nextstep.ladder.domain.Line;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class LinesTest {

    @Test
    @DisplayName("사다리의 수평선이 겹치면 예외를 던진다.")
    void horizontal_lines_overlapping_exception() {
        // when // then
        assertThatThrownBy(() -> new Lines(createOverlappingLines()))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage(HORIZONTAL_LINE_OVERLAPPING_EXCEPTION);
    }

    private List<Line> createOverlappingLines() {
        Line line1 = Line.createLineWithPointStatus(List.of(true, false, true));
        Line line = Line.createLineWithPointStatus(List.of(false, true, false));
        Line line3 = Line.createLineWithPointStatus(List.of(false, true, true));
        return List.of(line1, line, line3);
    }

    @Test
    @DisplayName("사다리의 줄 들의 길이가 다르면 예외들 던진다.")
    void not_same_line_length_exception() {
        // when // then
        assertThatThrownBy(() -> new Lines(createDifferentLengthLines()))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage(LINE_LENGTH_DIFFERENCE_EXCEPTION);
    }

    private List<Line> createDifferentLengthLines() {
        Line line1 = Line.createLineWithPointStatus(List.of(true, false, true));
        Line line = Line.createLineWithPointStatus(List.of(false, true, false));
        Line line3 = Line.createLineWithPointStatus(List.of(false, false, false, true));
        return List.of(line1, line, line3);
    }

    @ParameterizedTest
    @DisplayName("null이나 빈 lines가 들어오면 예외를 던진다.")
    @NullAndEmptySource
    void null_and_empty(List<Line> given) {
        // when // then
        assertThatThrownBy(() -> new Lines(given))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage(LINES_EMPTY_EXCEPTION);
    }
}