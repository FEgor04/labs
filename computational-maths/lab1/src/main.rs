use std::{env, fs::File, io::BufRead};

use matrix::DMatrix;

use crate::{
    reader::{FileReader, Reader, StdinReader},
    sle::SLE,
    solver::{
        gauss_seidel::{GaussSeidelSolver, MAX_ITERATIONS_COUNT},
        SLESolver,
    },
};

mod field;
pub mod matrix;
mod reader;
mod ring;
mod sle;
mod solver;

pub fn print_matrix<T: Copy + std::fmt::Display>(matrix: &DMatrix<T>) {
    let splitter = if matrix.get_ncols() == 1 { " " } else { "\t" };
    for i in 0..matrix.get_nrows() {
        for j in 0..matrix.get_ncols() {
            print!("{:.3}{}", matrix.get(i, j), splitter);
        }
        if matrix.get_ncols() != 1 {
            println!("");
        }
    }
    println!("")
}

fn main() {
    let args: Vec<String> = env::args().collect();
    let reader: Box<dyn Reader<f64>> = if args.len() < 2 {
        Box::new(StdinReader::new())
    } else {
        let file = File::open(args[1].clone()).unwrap();
        Box::new(FileReader::new(file))
    };

    let mut sle = SLE {
        n: reader.get_n(),
        a: reader.get_a(),
        b: reader.get_b(),
    };

    if !sle.is_diagonally_dominant() {
        println!("Диагональное преобладание не выполняется.");
        sle.transform_to_diagonally_dominant();
        if !sle.is_diagonally_dominant() {
            println!(
                "Невозможно привести систему к диагональному преобладанию. Продолжаем дальше."
            );
        } else {
            println!("Система была приведена к диагональному преобладанию. Матрица A:");
            print_matrix(&sle.a);
            println!("Вектор B");
            print_matrix(&sle.b);
        }
    }

    let solver = GaussSeidelSolver {
        precision: reader.get_precision(),
    };
    println!("Начинаю высчитывать для матрицы A:");
    print_matrix(&sle.a);
    println!("Вектор B:");
    print_matrix(&sle.b);
    let solution = solver.solve_sle(&sle);
    if solution.iterations_count < MAX_ITERATIONS_COUNT {
        println!("\n\n==== Решение системы ====");
        print_matrix(&solution.solution);
        println!(
            "Решение было достигнуто за {} итераций",
            solution.iterations_count
        );
        println!("Вектор ошибок:");
        print_matrix(&solution.error_vector.abs());
        let r = sle.a * solution.solution + (-sle.b);
        println!("Невязки:");
        print_matrix(&r);
    } else {
        println!(
            "\n\nРешение не было достигнуто за {} итераций.",
            MAX_ITERATIONS_COUNT
        );
    }
}
