# Reframe-Todo-List

## Overview
Simple todo-list app written with ClojureScript and Reframe

## Used tech stacks
- ClojureScript
- Leiningen / Figwheel
- Reagent : Simple React wrapper
- Reframe : SPA framework for Reagent
- bidi : URL routing
- pushy : HTML5 pushState
- Ring/Compojure : simple API server

## Features
- Creating/Deleting todo item
- Toggling todo items
- Filtering todo items with URL routing
- Counting number of active/done todos

## Usage
- `lein figwheel`

## Todos
- [ ] Add test cases
- [ ] Add `cljs.spec.alpha` validation
- [x] Save data to server (or localStorage)
- [ ] Inline editing
- [ ] Clearing todo items
