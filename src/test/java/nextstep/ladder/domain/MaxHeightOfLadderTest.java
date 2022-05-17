package nextstep.ladder.domain;

import nextstep.ladder.domain.exceptions.NotAllowNegativeOrZero;
import nextstep.ladder.domain.exceptions.NotNumberStringIsNotAllowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MaxHeightOfLadderTest {

    @Nested
    class 생성자는 {

        @Nested
        class 숫자가_아닌_문자열이_주어질경우 {

            @ParameterizedTest
            @NullAndEmptySource
            @ValueSource(strings = "a")
            void NotNumberStringIsNotAllowException을_던진다(String s) {
                assertThatThrownBy(() -> new MaxHeightOfLadder(s))
                        .isInstanceOf(NotNumberStringIsNotAllowException.class);
            }

        }

        @Nested
        class 숫자_문자열이_주어질경우 {

            @ParameterizedTest
            @ValueSource(strings = {"1", "11"})
            void 에러가_발생하지_않는다(String numberString) {
                assertThatCode(() -> new MaxHeightOfLadder(numberString))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        class 음수나_0이_주어질경우 {

            @ParameterizedTest
            @ValueSource(ints = {-1, 0})
            void NotAllowNegativeOrZero을_던진다(int negativeOrZero) {
                assertThatThrownBy(() -> new MaxHeightOfLadder(negativeOrZero))
                        .isInstanceOf(NotAllowNegativeOrZero.class);
            }
        }

        @Nested
        class _0보다_큰값이_주어질경우 {

            @ParameterizedTest
            @ValueSource(ints = {1, 2})
            void 에러가_발생하지_않는다(int overThenZero) {
                assertThatCode(() -> new MaxHeightOfLadder(overThenZero))
                        .doesNotThrowAnyException();
            }
        }

    }

    @Nested
    class getLines_메서드는 {
        private Members members;
        private MaxHeightOfLadder maxHeightOfLadder;

        @BeforeEach
        void setUp() {
            this.members = Members.of(Arrays.asList("pobi", "jason", "pang", "nem"));
            this.maxHeightOfLadder = new MaxHeightOfLadder(4);
        }

        @Nested
        class booleanGenerate_true일경우 {

            @Test
            void lines_검증() {
                List<Boolean> booleans = Arrays.asList(true, false, true);
                List<List<Boolean>> lists = Arrays.asList(booleans, booleans, booleans, booleans);

                assertThat(maxHeightOfLadder.getLines(members, () -> true))
                        .isEqualTo(Lines.of(lists));
            }
        }

        @Nested
        class booleanGenerator_false일경우 {

            @Test
            void lines_검증() {
                List<Boolean> booleans = Arrays.asList(false, false, false);
                List<List<Boolean>> lists = Arrays.asList(booleans, booleans, booleans, booleans);

                assertThat(maxHeightOfLadder.getLines(members, () -> false))
                        .isEqualTo(Lines.of(lists));
            }
        }

    }
}