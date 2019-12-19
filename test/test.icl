println let
    r : ref int = new 0
in
    r := if (!r > 0) then
        10
    else
        1
    end
end
;;