package nextstep.ladder.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Points {
    private static final int START_INCLUSIVE = 0;
    private static final int LAST_SIZE = 1;

    private final List<Point> points;

    public Points(List<Point> points) {
        this.points = points;
    }

    public static Points of(List<Boolean> points) {
        return new Points(toPoints(points));
    }

    public static Points of(Members members, BooleanGenerator booleanGenerator) {
        return new Points(toPoints(members, booleanGenerator));
    }

    private static List<Point> toPoints(List<Boolean> points) {
        return points.stream()
                .map(Point::new)
                .collect(Collectors.toList());
    }

    private static List<Point> toPoints(Members members, BooleanGenerator booleanGenerator) {
        List<Point> points = new ArrayList<>();
        IntStream.range(START_INCLUSIVE, members.size() - LAST_SIZE)
                .forEach(setPoints(booleanGenerator, points));

        return points;
    }

    private static IntConsumer setPoints(BooleanGenerator booleanGenerator, List<Point> points) {
        return i -> {
            if (previousHasEdge(points, i)) {
                points.add(new Point(false));
                return;
            }

            points.add(Point.of(booleanGenerator));
        };
    }

    private static boolean previousHasEdge(List<Point> points, int i) {
        if (points.isEmpty()) {
            return false;
        }

        return points.get(i - LAST_SIZE)
                .isTrue();
    }

    public String getPoint() {
        return this.points.stream()
                .map(Point::getEdgeOrEmpty)
                .collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Points points1 = (Points) o;
        return Objects.equals(points, points1.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }
}