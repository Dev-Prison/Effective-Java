package com.programmers.java.chapt6.item37;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

public enum Phase {
    SOLID, LIQUID, GAS, PLASMA;

    public enum Transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID),
        IONIZE(GAS, PLASMA), DEIONIZE(PLASMA, GAS);

        private final Phase from;
        private final Phase to;

        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        private static final Map<Phase, Map<Phase, Transition>> m =
            Stream.of(values()) // enum 타입 두 개를 매핑한 필드 리스트
                .collect(
                    groupingBy(
                        t -> t.from,
                        () -> new EnumMap<>(Phase.class),
                        toMap(
                            t -> t.to,
                            t -> t,
                            (x, y) -> y,
                            () -> new EnumMap<>(Phase.class)
                        )
                    )
                );

        public static Transition from(Phase from, Phase to) {
            return m.get(from).get(to);
        }
    }
}

/* -- ordinal 사용
pu용lic enum Phase {
    SOLID,
    LIQUID,
    GAS;

    public enum Transition {
        MELT,
        FREEZE,
        BOIL,
        CONDENSE,
        SUBLIME,
        DEPOSIT;

        private static final Transition[][] TRANSITIONS = {
            {null, MELT, SUBLIME},
            {FREEZE, null, BOIL},
            {DEPOSIT, CONDENSE, null}
        };

        public static Transition from(Phase from, Phase to) {
            return TRANSITIONS[from.ordinal()][to.ordinal()];
        }
    }

}
 */