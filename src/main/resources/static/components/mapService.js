var mapServiceModule = angular.module('IndoorApp.mapService', []);

// Map service
function MapService() {
    // styles
    var calc_marker_style = {
        image: {
            icon: {
                anchor: [0.5, 0.5],
                anchorXUnits: 'fraction',
                anchorYUnits: 'fraction',
                opacity: 1.0,
                src: 'assets/icons/calc-marker.png'
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
                src: 'assets/icons/ref-marker.png'
            }
        }
    };

    var no_ref_calc_marker_style = {
        image: {
            icon: {
                anchor: [0.5, 0.5],
                anchorXUnits: 'fraction',
                anchorYUnits: 'fraction',
                opacity: 1.0,
                src: 'assets/icons/norefcalc-marker.png'
            }
        }
    };

    var selected_marker_style = {
        image: {
            icon: {
                anchor: [0.5, 0.5],
                anchorXUnits: 'fraction',
                anchorYUnits: 'fraction',
                opacity: 1.0,
                src: 'assets/icons/selected-marker.png'
            }
        }
    };

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

    var calcPointInfo = [];
    var calcPointFeatures = [];
    var calcFeatures = {
        type: 'FeatureCollection',
        features: calcPointFeatures
    };
    var calcLayer = {
        name: 'calclayer',
        index: 5,
        source: {
            type: 'GeoJSON',
            geojson: {
                object: calcFeatures
            }
        },
        style: calc_marker_style,
        visible: true,
        opacity: 1
    };

    var noRefCalcPointInfo = [];
    var noRefCalcPointFeatures = [];
    var noRefCalcFeatures = {
        type: 'FeatureCollection',
        features: noRefCalcPointFeatures
    };
    var noRefCalcLayer = {
        name: 'norefcalclayer',
        index: 3,
        source: {
            type: 'GeoJSON',
            geojson: {
                object: noRefCalcFeatures
            }
        },
        style: no_ref_calc_marker_style,
        visible: false,
        opacity: 1
    };

    var refPointInfo = [];
    var refPointFeatures = [];
    var refFeatures = {
        type: 'FeatureCollection',
        features: refPointFeatures
    };
    var refLayer = {
        name: 'reflayer',
        index: 4,
        source: {
            type: 'GeoJSON',
            geojson: {
                object: refFeatures
            }
        },
        style: ref_marker_style,
        visible: true,
        opacity: 1
    };

    // label cache
    var labelChache = [];

    // map objects
    var pathsLayerObject = {};

    // map service properties
    var staticMap = {
        index: 0
    };
    var mDefaults = {
        interactions: {
            mouseWheelZoom: true
        },
        events:{
            layers: ['click'],
            map: ['singleclick']
        },
        render: 'webgl'
    };
    var mCenter = {
        zoom: 2
    };

    // mirror function
    function mirrorY(originalY) {
        return staticMap.source.imageSize[1] - originalY;
    }

    //clear lable function
    var clearLabel = function () {
        labelChache.length = 0;
    };

    // map service access functions
    return {
        // lines
        pathsLayer: function () {
            return pathsLayerObject;
        },
        pointLabels: function () {
            // return copy of list
            return [].concat(labelChache);
        },
        // calculated points
        calcPointLayer: function () {
            // return copy of list
            return calcLayer;
        },
        addCalcLabel: function (x, y, error) {
            // Y needs mirroring because start of map is at bottom
            var mirroredY = mirrorY(y);
            // create a new calculated point
            var newCalc = {
                coord: [x, mirroredY],
                projection: 'pixel',
                style: selected_marker_style,
                label: {
                    message: "<p>X: " + x + "</p><p>Y: " + y + "</p><p>error: " + error + " m</p>",
                    show: true
                }
            };
            labelChache.push(newCalc)
        },
        addCalcPoint: function (x, y, error) {
            var mirroredY = mirrorY(y);
            var newId = calcPointFeatures.length;
            newCalcFeature = {
                type: 'Feature',
                id: newId,
                geometry: {
                    type: 'Point',
                    coordinates: [x, mirroredY]
                },
                style: calc_marker_style
            };
            calcPointInfo[newId] = {
                x: x,
                y: y,
                mirroredY: mirroredY,
                error: error
            };
            calcPointFeatures.push(newCalcFeature);
        },
        hideCalcPoints: function () {
            clearLabel();
            calcLayer.visible = false;
        },
        showCalcPoints: function () {
            calcLayer.visible = true;
        },
        getCalcPointInfo: function (id) {
            return calcPointInfo[id];
        },
        // calculated points
        noRefCalcPointLayer: function () {
            // return copy of list
            return noRefCalcLayer;
        },
        addNoRefCalcLabel: function (x, y) {
            // Y needs mirroring because start of map is at bottom
            var mirroredY = mirrorY(y);
            // create a new calculated point
            var newCalc = {
                coord: [x, mirroredY],
                projection: 'pixel',
                style: selected_marker_style,
                label: {
                    message: "<p>X: " + x + "</p><p>Y: " + y + "</p><p>Error: No reference available</p>",
                    show: true
                }
            };
            labelChache.push(newCalc)
        },
        addNoRefCalcPoint: function (x, y) {
            var newId = noRefCalcPointFeatures.length;
            var mirroredY = mirrorY(y);
            newNoRefCalcFeature = {
                id: newId,
                type: 'Feature',
                geometry: {
                    type: 'Point',
                    coordinates: [x, mirroredY]
                }
            };
            noRefCalcPointInfo[newId] = {
                x: x,
                y: y,
                mirroredY: mirroredY
            };
            noRefCalcPointFeatures.push(newNoRefCalcFeature);
        },
        hideNoRefCalcPoints: function () {
            clearLabel();
            noRefCalcLayer.visible = false;
        },
        showNoRefCalcPoints: function () {
            noRefCalcLayer.visible = true;
        },
        getNoRefCalcPointInfo: function (id) {
            return noRefCalcPointInfo[id];
        },
        // reference points
        refPointLayer: function () {
            // return copy of list
            return refLayer;
        },
        addRefLabel: function (x, y) {
            // Y needs mirroring because start of map is at bottom
            var mirroredY = mirrorY(y);
            // create a new calculated point
            var newRef = {
                coord: [x, mirroredY],
                projection: 'pixel',
                style: selected_marker_style,
                label: {
                    message: "<p>X: " + x + "</p><p>Y: " + y + "</p>",
                    show: true
                }
            };
            labelChache.push(newRef)
        },
        addRefPoint: function (x, y) {
            var newId = refPointFeatures.length;
            var mirroredY = mirrorY(y);
            newRefFeature = {
                id: newId,
                type: 'Feature',
                geometry: {
                    type: 'Point',
                    coordinates: [x, mirroredY]
                }
            };
            refPointInfo[newId] = {
                x: x,
                y: y,
                mirroredY: mirroredY
            };
            refPointFeatures.push(newRefFeature);
        },
        hideRefPoints: function () {
            clearLabel();
            refLayer.visible = false;
        },
        showRefPoints: function () {
            refLayer.visible = true;
        },
        getRefPointInfo: function (id) {
            return refPointInfo[id];
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
            calcPointFeatures.length = 0;
            noRefCalcPointFeatures.length = 0;
            refPointFeatures.length = 0;
            clearLabel();
        },
        clearLabels: function () {
            clearLabel();
        }
    };
}

mapServiceModule.factory('mapService', MapService);