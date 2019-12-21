println let
    adder: ((int)int, int)int =
        fun f: (int)int,
            n: int ->
            f(n)
        end
    inc : (int)int =
    fun x : int ->
        x + 1
    end
in
    adder(inc, 1)
end
;;