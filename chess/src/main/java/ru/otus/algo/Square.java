package ru.otus.algo;

public enum Square {

    A1(0),  B1(1),  C1(2),  D1(3),  E1(4),  F1(5),  G1(6),  H1(7),
    A2(8),  B2(9),  C2(10), D2(11), E2(12), F2(13), G2(14), H2(15),
    A3(16), B3(17), C3(18), D3(19), E3(20), F3(21), G3(22), H3(23),
    A4(24), B4(25), C4(26), D4(27), E4(28), F4(29), G4(30), H4(31),
    A5(32), B5(33), C5(34), D5(35), E5(36), F5(37), G5(38), H5(39),
    A6(40), B6(41), C6(42), D6(43), E6(44), F6(45), G6(46), H6(47),
    A7(48), B7(49), C7(50), D7(51), E7(52), F7(53), G7(54), H7(55),
    A8(56), B8(57), C8(58), D8(59), E8(60), F8(61), G8(62), H8(63);

    private static final Square[]  of = {A1, B1, C1, D1, E1, F1, G1, H1,
                                         A2, B2, C2, D2, E2, F2, G2, H2,
                                         A3, B3, C3, D3, E3, F3, G3, H3,
                                         A4, B4, C4, D4, E4, F4, G4, H4,
                                         A5, B5, C5, D5, E5, F5, G5, H5,
                                         A6, B6, C6, D6, E6, F6, G6, H6,
                                         A7, B7, C7, D7, E7, F7, G7, H7,
                                         A8, B8, C8, D8, E8, F8, G8, H8};
    private int value;

    Square(int value) {
        this.value = value;
    }

    public static Square of(int i) {
        assert  i < 64;
        return of[i];
    }

    public int getValue() {
        return value;
    }
}
