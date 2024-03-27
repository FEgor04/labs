AUX_DIR := aux

DEP_FILE := .deps.d

LATEXMK_FLAGS := -pdf -pdflatex="pdflatex -interaction=nonstopmode" \
								 -use-make -auxdir=$(AUX_DIR) 											\
								 -M -MF $(DEP_FILE)

.PHONY: clean format pvc spell

all: main.pdf

main.pdf: main.tex $(AUX_DIR)
	latexmk $(LATEXMK_FLAGS) $<

pvc: main.tex
	latexmk $(LATEXMK_FLAGS) -pvc $<

clean:
	rm -rf $(AUX_DIR) $(DEP_FILE) main.pdf **/*.bak* src/**/*.bak* *.bbl *.nav *.run.xml *.snm **/*.log src/*/*.log
	latexmk -C main.tex 

format:
	latexindent src/*.tex -w

spell:
	hunspell -d ru_RU,en_US -l -t src/*.tex main.tex

venv/bin/activate:
	python3 -m venv $@

venv/dependencies_touchfile: venv/bin/activate
	source $<; pip install numpy matplotlib
	touch $@

$(FIGURES_DIR) $(AUX_DIR):
	mkdir $@

$(DEP_FILE):
	touch $@

-include $(DEP_FILE)
