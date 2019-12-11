let x: (int, (int, int) int) int =
    fun a: int,
        b: (int, int) int ->
        a + b(a,2)
    end
in
    let y: (int,int) int =
        fun c: int,
            d: int ->
            c + d
        end
    in
        x(5,y)
    end
end;;