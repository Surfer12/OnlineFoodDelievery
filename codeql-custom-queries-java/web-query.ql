/**
 * This is a custom CodeQL query to find all @WebServlet annotations in Java code.
 * @name Find WebServlet Annotations
 * @kind problem
 * @problem.severity warning
 * @id java/web/find-webservlet
 */

import java

from Method m
where m.getAnAnnotation().getType().hasName("javax.servlet.annotation.WebServlet")
select m, "Found @WebServlet annotation in method: " + m.getName()

