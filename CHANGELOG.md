# Change Log

The format is based on [Keep a Changelog](http://keepachangelog.com/).

## [3.0] - 2025-01-28
### Added
- `build.yml`, `deploy.yml`: converted project to GitHub Actions
### Changed
- `pom.xml`: moved to `io.jstuff` (package amd Maven group)
### Removed
- `.travis.yml`

## [2.5] - 2024-02-18
### Changed
- `ImmutableCollection`: fixed bug in indexed access
- `pom.xml`, tests: switched from Junit 5 to Junit 4 (more stable)
- `pom.xml`: updated plugin versions

## [2.4] - 2023-09-24
### Changed
- `ImmutableCollection`, `ImmutableList`, `ImmutableMap`, `ImmutableSet`: added clarifying JavaDoc
- `MiniMap0`, `MiniSet0`: minor optimisations
- `pom.xml`: fixed repository reference
- `ImmutableCollection`, `ImmutableList`, `ImmutableSet`: optimised constructors (removed duplicate length checks)
- `ImmutableIterator`, `ImmutableListIterator`: made constructors public
- `ImmutableList`: optimised `sublist()`
- `ImmutableList`, `ImmutableMap`, `ImmutableSet`: modified static functions to use optimised constructors

## [2.3] - 2023-01-08
### Changed
- `ImmutableMap`: added `getEntry()`, `getKey()` and `getValue()` functions

## [2.2] - 2022-05-28
### Changed
- `ImmutableCollection`, `ImmutableList`, `ImmutableMap`, `ImmutableSet`: add copy constructors (helps deserialization)
- `MiniMap(n)`, `MiniSet(n)`: add copy constructors
- `MiniMap(n)`, `MiniSet(n)`: improve null handling

## [2.1] - 2022-04-18
### Changed
- `ImmutableCollection`, `ImmutableList`, `ImmutableMap`: add indexed access to all collection types (to allow for
  optimised iteration over sets, maps)

## [2.0] - 2022-01-21
### Changed
- `ImmutableMap`: switched to use `ImmutableMapEntry` (potential breaking change)
### Added
- `ImmutableMapEntry`: extracted from `ImmutableMap`
- `MiniSet`, `MiniMap`: and implementing classes

## [1.2] - 2021-08-24
### Changed
- `ImmutableMap`: fixed bug in `equals()`

## [1.1] - 2021-07-16
### Changed
- all files: added JavaDoc
- `ImmutableCollection`, `ImmutableList`, `ImmutableMap`, `ImmutableSet`: added length checks
- several: minor optimisations
- several: reduced visibility of constructors and data fields

## [1.0] - 2021-07-09
### Added
- all files: initial implementations
