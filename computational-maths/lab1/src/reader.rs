use std::fmt::Debug;
use std::fs::File;
use std::io::Read;
use std::str::FromStr;

use crate::matrix::DMatrix;

pub trait Reader<T> {
    fn get_n(&self) -> usize;
    fn get_precision(&self) -> T;
    fn get_a(&self) -> DMatrix<T>;
    fn get_b(&self) -> DMatrix<T>;
}

pub struct StdinReader<T: Copy> {
    n: usize,
    precision: T,
    a: DMatrix<T>,
    b: DMatrix<T>,
}

impl<T: Copy> Reader<T> for StdinReader<T> {
    fn get_n(&self) -> usize {
        self.n
    }

    fn get_precision(&self) -> T {
        self.precision
    }

    fn get_a(&self) -> DMatrix<T> {
        self.a.clone()
    }

    fn get_b(&self) -> DMatrix<T> {
        self.b.clone()
    }
}

fn read_string() -> String {
    let mut input = String::new();
    std::io::stdin()
        .read_line(&mut input)
        .expect("can not read user input");
    input
}

fn read_matrix<T: Copy + FromStr>(n_rows: usize) -> DMatrix<T>
where
    <T as FromStr>::Err: Debug,
{
    let mut rows = Vec::<Vec<T>>::new();
    for _ in 0..n_rows {
        let row: Vec<T> = read_string()
            .trim()
            .split(" ")
            .map(|x| x.parse::<T>().unwrap())
            .collect();
        rows.push(row);
    }
    DMatrix::new(rows)
}

impl<T: Copy + FromStr> StdinReader<T> {
    pub fn new() -> Self
    where
        <T as FromStr>::Err: Debug,
    {
        println!("Введите количество уравнений");
        let n = read_string().trim().parse::<usize>().unwrap();
        println!("Введите точность вычислений");
        let precision = read_string().trim().parse::<T>().unwrap();
        println!("Введите матрицу A");
        let a = read_matrix(n);
        println!("Введите вектор B");
        let b = read_matrix(n);
        Self { n, precision, a, b }
    }
}

pub struct FileReader<T> {
    n: usize,
    precision: T,
    a: DMatrix<T>,
    b: DMatrix<T>,
}

impl<T: Copy> Reader<T> for FileReader<T> {
    fn get_n(&self) -> usize {
        self.n
    }

    fn get_precision(&self) -> T {
        self.precision
    }

    fn get_a(&self) -> DMatrix<T> {
        self.a.clone()
    }

    fn get_b(&self) -> DMatrix<T> {
        self.b.clone()
    }
}

impl<T: FromStr + Copy> FileReader<T>
where
    <T as FromStr>::Err: Debug,
{
    pub fn new(mut file: File) -> Self {
        let mut buf = String::new();
        file.read_to_string(&mut buf);
        let lines: Vec<_> = buf.split("\n").collect();
        let n = lines[0].trim().parse::<usize>().unwrap();
        let precision = lines[1].trim().parse::<T>().unwrap();
        let a: DMatrix<T> = FileReader::read_matrix(&lines[2..n + 2], n);
        let b: DMatrix<T> = FileReader::read_matrix(&lines[n + 2..2 * n + 2], n);
        Self { n, a, b, precision }
    }

    fn read_matrix(lines: &[&str], n: usize) -> DMatrix<T> {
        let rows: Vec<Vec<_>> = (&lines[0..n])
            .iter()
            .map(|row| {
                row.trim()
                    .split(" ")
                    .map(|x| x.parse::<T>().unwrap())
                    .collect()
            })
            .collect();
        DMatrix::new(rows)
    }
}
