#                                               -*- Autoconf -*-
# Process this file with autoconf to produce a configure script.

AC_PREREQ([2.69])
AC_INIT([xml-schema-validator], [0.1], [wr@gatech.edu])
AC_COPYRIGHT
# AC_PREFIX_DEFAULT($HOME)
# AM_INIT_AUTOMAKE([-Wall -Werror gnu])

# Checks for programs.
AC_PROG_INSTALL
AC_CONFIG_SRCDIR
AC_PROG_INSTALL
AC_PROG_MKDIR_P
AC_PROG_GREP
AC_PROG_SED

dnl WR_check_prog( $command-name )
dnl   e.g., WR_check_prog(bash) will set @bash@
AC_DEFUN([WR_check_prog],[AC_PATH_PROG($1,$1)
  test "x$[]$1" = "x" && AC_MSG_ERROR([$1 not found])])dnl

dnl WR_check_prog_options( $command-short-name, $command-name-options ... )
dnl   e.g., WR_check_prog_options(cat, gcat cat) will set @cat@
AC_DEFUN([WR_check_prog_options],[AC_PATH_PROGS($1,$2)
  test "x$[]$1" = "x" && AC_MSG_ERROR([$1 not found])])dnl

WR_check_prog(bash)
WR_check_prog(gradle)
WR_check_prog_options(find,gfind find)
WR_check_prog_options(m4,gm4 m4)
WR_check_prog(touch)

AC_PATH_PROGS(stow,stow,stow)
AC_PATH_PROGS(zip,zip,zip)

AC_CONFIG_FILES([Makefile], [chmod 444 Makefile])
AC_CONFIG_FILES([stow.mk], [chmod 444 stow.mk])
AC_OUTPUT
