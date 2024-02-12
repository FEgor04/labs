use std::ops::Div;

use crate::ring::RingElement;

pub trait FieldElement: RingElement + Div<Self, Output = Self> {}

impl FieldElement for f64 {}
impl FieldElement for i32 {}
