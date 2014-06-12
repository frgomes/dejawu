*dejawu* is an exploratory tentative to provide Dojo Toolkit with ScalaJS


Quick guide for the impatient
=============================

::

    $ sbt update clean run
    $ firefox http://localhost:9000/purejs # for HTML + JS
    $ firefox http://localhost:9000/app    # for HTML + scala-js


Status
======

1. Code based on scalatags
2. Basic skeleton which explores the organization of the code
3. Just a few graphical and non-graphical components at the moment.

Next steps
==========

1. Employ code generation as much as possible as a first step.
   The idea is employing the SBT build for code generation, reading a configuration file
   and creating simple wrappers as much as we can.
   See: https://github.com/frgomes/dejawu/blob/master/project/widgets.properties

Medium term goals
=================
   
11. Provide more flexible data stores, with coherent data structures and type checking
12. Provide reactive data stores
13. Add a grid widget

Long term goals
===============

101. Create a single-page demo similar to http://demos.dojotoolkit.org/demos/themePreviewer/demo.html
102. Create a demo similar to http://demos.dojotoolkit.org/demos/
