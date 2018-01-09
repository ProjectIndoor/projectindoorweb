var mapServiceModule = angular.module('IndoorApp.mapService', []);

// Map service
function MapService() {
    var lines = [];
    // line test
    var errorLineFeatures = {
        type: 'FeatureCollection',
        features: [
            {
                type: 'Feature',
                id: 'ERRORLINES',
                properties: {
                    name: 'ErrorLines'
                },
                geometry: {
                    type: 'MultiLineString',
                    coordinates: lines
                }
            }
        ]
    };

    //errorLineFeatures.features[0].geometry.coordinates.push(lines);

    var errorLineLayer = {
        index: 2,
        source: {
            type: 'GeoJSON',
            geojson: {
                object: errorLineFeatures
            }
        },
        style: {
            fill: {
                color: 'rgba(255, 0, 0, 0.6)'
            },
            stroke: {
                color: '#CC6666',
                lineDash: [.1, 6],
                width: 3
            }
        },
        visible: true,
        opacity: 1
    };

    // map object hide cache
    var loadedNoRefPos = [];
    var loadedCalcPos = [];
    var loadedRefs = [];
    var emptyPoints = [];

    // map objects
    var pathsLayerObject = {};
    var calculatedPoints = loadedCalcPos;
    var noRefCalculatedPoints = emptyPoints;
    var referencePoints = loadedRefs;

    // map service properties
    var staticMap = {
        index: 0
    };
    var mDefaults = {
        interactions: {
            mouseWheelZoom: true
        }
    };
    var mCenter = {
        zoom: 2
    };

    // styles
    var calc_marker_style = {
        image: {
            icon: {
                anchor: [0.5, 0.5],
                anchorXUnits: 'fraction',
                anchorYUnits: 'fraction',
                opacity: 1.0,
                src: 'icons/calc-marker.png'
            }
        }
    };
    var ref_marker_style = {
        image: {
            icon: {
                anchor: [0.5, 0.5],
                anchorXUnits: 'fraction',
                anchorYUnits: 'fraction',
                opacity: 1.0,
                src: 'icons/ref-marker.png'
            }
        }
    };

    // mirror function
    function mirrorY(originalY) {
        return staticMap.source.imageSize[1] - originalY;
    }

    // map service access functions
    return {
        // lines
        pathsLayer: function () {
            return pathsLayerObject;
        },
        // calculated points
        calcPoints: function () {
            // return copy of list
            return [].concat(calculatedPoints);
        },
        addCalcPoint: function (x, y, error) {
            // Y needs mirroring because start of map is at bottom
            var mirroredY = mirrorY(y);
            // create a new calculated point
            var newCalc = {
                coord: [x, mirroredY],
                projection: 'pixel',
                style: calc_marker_style,
                label: {
                    message: "<p>X: " + x + "</p><p>Y: " + y + "</p><p>error: " + error + " m</p>",
                    show: false,
                    showOnMouseOver: true
                }
            };
            loadedCalcPos.push(newCalc)
        },
        hideCalcPoints: function () {
            calculatedPoints = emptyPoints;
        },
        showCalcPoints: function () {
            calculatedPoints = loadedCalcPos;
        },
        // calculated points
        noRefCalcPoints: function () {
            // return copy of list
            return [].concat(noRefCalculatedPoints);
        },
        addNoRefCalcPoint: function (x, y) {
            // Y needs mirroring because start of map is at bottom
            var mirroredY = mirrorY(y);
            // create a new calculated point
            var newCalc = {
                coord: [x, mirroredY],
                projection: 'pixel',
                style: calc_marker_style,
                label: {
                    message: "<p>X: " + x + "</p><p>Y: " + y + "</p><p>Error: No reference available</p>",
                    show: false,
                    showOnMouseOver: true
                }
            };
            loadedNoRefPos.push(newCalc)
        },
        hideNoRefCalcPoints: function () {
            noRefCalculatedPoints = emptyPoints;
        },
        showNoRefCalcPoints: function () {
            noRefCalculatedPoints = loadedNoRefPos;
        },
        // reference points
        refPoints: function () {
            // return copy of list
            return [].concat(referencePoints);
        },
        addRefPoint: function (x, y) {
            // Y needs mirroring because start of map is at bottom
            var mirroredY = mirrorY(y);
            // create a new calculated point
            var newRef = {
                coord: [x, mirroredY],
                projection: 'pixel',
                style: ref_marker_style,
                label: {
                    message: "<p>X: " + x + "</p><p>Y: " + y + "</p>",
                    show: false,
                    showOnMouseOver: true
                }
            };
            loadedRefs.push(newRef)
        },
        hideRefPoints: function () {
            referencePoints = emptyPoints;
        },
        showRefPoints: function () {
            referencePoints = loadedRefs;
        },
        // Access map, defaults and center
        map: function () {
            return staticMap;
        },
        mapDefaults: function () {
            return mDefaults;
        },
        mapCenter: function () {
            return mCenter;
        },
        setMap: function (mapUrl, width, height) {
            // set map image
            staticMap.source = {
                type: "ImageStatic",
                url: mapUrl,
                imageSize: [width, height]
            };
            // set view size
            mDefaults.view = {
                projection: 'pixel',
                extent: [0, 0, width, height]
            };
            // set view center
            mCenter.coord = [Math.floor(width / 2), Math.floor(height / 2)];
        },
        displayLines: function () {
            pathsLayerObject = errorLineLayer;
        },
        showLines: function () {
            pathsLayerObject.visible = true;
        },
        hideLines: function () {
            pathsLayerObject.visible = false;
        },
        addErrorLine: function (calcX, calcY, refX, refY) {
            var newLine = [
                [
                    calcX,
                    mirrorY(calcY)
                ],
                [
                    refX,
                    mirrorY(refY)
                ]
            ];
            lines.push(newLine);
        },
        clearMap: function () {
            // empty arrays
            lines.length = 0;
            loadedCalcPos.length = 0;
            loadedNoRefPos.length = 0;
            loadedRefs.length = 0;
        }
    };
}

mapServiceModule.factory('mapService', MapService);