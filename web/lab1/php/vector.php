<?php
class Vector2D
{
    private $x, $y;

    public function __construct($x, $y)
    {
        $this->x = $x;
        $this->y = $y;
    }

    public function minus(Vector2D $v2)
    {
        return new Vector2D($this->x - $v2->x, $this->y - $v2->y);
    }

    public function product(Vector2D $v)
    {
        return $this->x * $v->y - $v->x * $this->y;
    }
}
?>