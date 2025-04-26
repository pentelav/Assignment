

let mean lst =
  let sum = List.fold_left (+) 0 lst in
  float_of_int sum /. float_of_int (List.length lst)

let median lst =
  let sorted = List.sort compare lst in
  let len = List.length sorted in
  if len mod 2 = 1 then
    float_of_int (List.nth sorted (len / 2))
  else
    let mid1 = List.nth sorted (len / 2 - 1)
    and mid2 = List.nth sorted (len / 2) in
    (float_of_int (mid1 + mid2)) /. 2.0

let mode lst =
  let count_map =
    List.fold_left (fun acc x ->
      if List.mem_assoc x acc then
        (x, List.assoc x acc + 1) :: List.remove_assoc x acc
      else
        (x, 1) :: acc
    ) [] lst in
  let max_count = List.fold_left (fun acc (_, c) -> max acc c) 0 count_map in
  List.fold_right (fun (k, v) acc -> if v = max_count then k :: acc else acc) count_map []

let () =
  let data = [1; 2; 2; 3; 4; 4; 5] in
  Printf.printf "Mean: %.2f\n" (mean data);
  Printf.printf "Median: %.2f\n" (median data);
  Printf.printf "Mode: ";
  List.iter (fun x -> Printf.printf "%d " x) (mode data);
  print_newline ()
