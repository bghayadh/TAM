
console.log("wowww");

function populateNodesTable(fromNode, toNode) {
    const toNodesTableBody = document.querySelector('#toNodes tbody');
    const fromNodesTableBody = document.querySelector('#fromNodes tbody');
    let rowcountNode = 0;

    toNodesTableBody.innerHTML = '';
    fromNodesTableBody.innerHTML = '';

    const toNodeArrayStr = toNode;
    let toNodeArray = [];
    if (toNodeArrayStr && toNodeArrayStr.trim() !== '') {
        try {
            toNodeArray = JSON.parse(toNodeArrayStr).toNodeArray || [];
        } catch (e) {
            console.error('Error parsing toNodeArray:', e);
        }
console.log(toNodeArray);
        if (Array.isArray(toNodeArray)) {
            toNodeArray.forEach(node => {
				if (!(node.NodeId === " " && node.NodeName === " " && node.NodeType === " ")) {
				     	console.log("woww");
                    const markup = `
                        <tr>
                            <td style="width:5%; text-align:center;">
                                <input type="checkbox" name="record" id="${node.NodeId}" style="margin-top:10px">
                            </td>
                            <td name="NodeId" style="width:31%;">
                                <input name="NodeToId" class="form-control text-input" type="text" id="NodeToId_${rowcountNode}" value="${node.NodeId}" style="width:100%;box-sizing:border-box;">
                            </td>
                            <td name="NodeName" style="width:31%;">
                                <input name="NodeToName" id="NodeToName_${rowcountNode}" value="${node.NodeName}" style="width:100%;box-sizing:border-box;" type="text" class="form-control text-input ui-widget ui-widget-content ui-corner-all">
                            </td>
                            <td name="NodeType" style="width:31%;">
                                <input name="NodeToType" id="NodeToType_${rowcountNode}" value="${node.NodeType}" style="width:100%;box-sizing:border-box;" type="text" class="form-control text-input ui-widget ui-widget-content ui-corner-all">
                            </td>
                        </tr>
                    `;
                    toNodesTableBody.innerHTML += markup;
                    rowcountNode++;
                }
            });
        }
    }

    rowcountNode = 0;

    const fromNodeArrayStr = fromNode;
    let fromNodeArray = [];
    if (fromNodeArrayStr && fromNodeArrayStr.trim() !== '') {
        try {
            fromNodeArray = JSON.parse(fromNodeArrayStr).fromNodeArray || [];
        } catch (e) {
            console.error('Error parsing fromNodeArray:', e);
        }

        if (Array.isArray(fromNodeArray)) {
            fromNodeArray.forEach(node => {
				if (!(node.NodeId === " " && node.NodeName === " " && node.NodeType === " ")) {
							
                    const markup = `
                        <tr>
                            <td style="width:5%; text-align:center;">
                                <input type="checkbox" name="record" id="${node.NodeId}" style="margin-top:10px">
                            </td>
                            <td name="NodeId" style="width:31%;">
                                <input name="NodeFromId" class="form-control text-input" type="text" id="NodeFromId_${rowcountNode}" value="${node.NodeId}" style="width:100%;box-sizing:border-box;">
                            </td>
                            <td name="NodeName" style="width:31%;">
                                <input name="NodeFromName" id="NodeFromName_${rowcountNode}" value="${node.NodeName}" style="width:100%;box-sizing:border-box;" type="text" class="form-control text-input ui-widget ui-widget-content ui-corner-all">
                            </td>
                            <td name="NodeType" style="width:31%;">
                                <input name="NodeFromType" id="NodeFromType_${rowcountNode}" value="${node.NodeType}" style="width:100%;box-sizing:border-box;" type="text" class="form-control text-input ui-widget ui-widget-content ui-corner-all">
                            </td>
                        </tr>
                    `;
                    fromNodesTableBody.innerHTML += markup;
                    rowcountNode++;
                }
            });
        }
    }
}


function getContextPath() {
		   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
		}




