all: main.pdf

emp_func.pdf hist.pdf polygon.pdf: python/1.py
	source venv/bin/activate; python3 $<

regression.pdf: python/2.py
	source venv/bin/activate; python3 $<

clean:
	rm -rf emp_func.pdf hist.pdf polygon.pdf regression.pdf *.bak*
	latexmk -C

main.pdf: main.tex emp_func.pdf hist.pdf polygon.pdf regression.pdf
	latexmk -pdf -use-make -pdflatex="pdflatex -interaction=nonstopmode"

format:
	latexindent main.tex -w

.PHONY: clean format
