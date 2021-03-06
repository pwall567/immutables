# Change Log

The format is based on [Keep a Changelog](http://keepachangelog.com/).

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
