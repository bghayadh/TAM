<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Tree View</h2>
	<p>A tree view represents a hierarchical view of information, where
		each item can have a number of subitems.</p>
	<p>Click on the arrow(s) to open or close the tree branches.</p>

	<div id="Tree">
		<ul id="myUL">
			<li><span class="caret">Beverages</span>
				<ul class="nested">
					<li>Water</li>
					<li>Coffee</li>
					<li><span class="caret">Tea</span>
						<ul class="nested">
							<li>Black Tea</li>
							<li>White Tea</li>
							<li><span class="caret">Green Tea</span>
								<ul class="nested">
									<li>Sencha</li>
									<li>Gyokuro</li>
									<li>Matcha</li>
									<li>Pi Lo Chun</li>
								</ul></li>
						</ul></li>
				</ul></li>
		</ul>


		<ul id="myUL" name="tr">
			<li>Test123</li>
		</ul>

		<ul id="myUL" name="tr">
			<li>Test222</li>
		</ul>

		<ul id="myUL" name="tr">
			<li>Test333</li>
		</ul>

		<ul id="myUL" name="tr">
			<li>Test444</li>
		</ul>

		<ul id="myUL" name="tr">
			<li>Test555</li>
			<ul id="myUL3">
				<li>TestXYZ</li>
				<ul id="myUL3">
					<li>TestWWW</li>
					<ul id="10091975">
						<li id="11223344>ABC>FFF" name="11>22>33">TestZZZ</li>
						<ul id="parentID_bilal">
							<li id="445566>778899">TestZZZ1</li>
						</ul>
						<ul>
							<li>TestZZZ2</li>
						</ul>
						<ul>
							<li>TestZZZ3</li>
							<ul id="L1">
								<li>TestZZZ3-1</li>
							</ul>
							<ul id="L2">
								<li>TestZZZ3-2</li>
							</ul>
						</ul>
					</ul>
				</ul>
			</ul>
			<ul id="myUL3">
				<li>TestBBB</li>
				<ul id="myUL3">
					<li>TestBBB1</li>
				</ul>
			</ul>
			<ul id="myUL3">
				<li>TestRRR</li>
			</ul>
		</ul>

		<ul id="myUL" name="tr">
			<li>Test666</li>
			<ul id="myUL3">
				<li>Test888</li>
			</ul>
			<ul id="myUL4">
				<li>Test999</li>
			</ul>
		</ul>

	</div>
</body>

<script type="text/javascript">
console
.log("Get the value of the li based on the ul id: "
		+ $('#10091975').find('li').attr(
				'id'));
console
.log("Get the value of the li based on the ul id: "
		+ $('#10091975')
				.children('ul > li').text());
console
.log("Get the value of the li based on the id#: 445566>778899: "
		+ $('#445566\\>778899').text());
console
.log("Get all the parents id of the li based on the id#: 445566>778899: "
		+ $('#445566\\>778899').parents()
				.attr('id'));
console
.log("Get the value of the li based on name: 11>22>33: "
		+ $('li[name="11\\>22\\>33"]')
				.text());
console
.log("Get the value of the li based on the id#: 11223344*ABC*FFF: "
		+ $('#11223344\\>ABC\\>FFF').text());

if ($('#11223344\\>ABC\\>FFF').length > 0)
console.log("got it");
else
console.log("not existed");

/*	console.log("Getting all parent ul " +$('ul[name="tr"]').parent().children().length);
console.log("Getting all parent ul " +$('ul[name="tr"] :parent li').length);
console.log("Getting all parent ul " +$('#Tree > ul[name="tr"]').length);
console.log("Getting all parent ul " +$('ul[name="tr"]').children().eq(0).text());
console.log("Getting all parent ul " +$('ul[name="tr"]').length);
console.log("Getting all parent ul that does not have ul: " +$('ul[name="tr"]:not(:has(ul))').length);
console.log("Getting text of childs of certail ul: " +$('ul[name="tr"]').eq(2).text());
console.log("Getting text of childs of certail ul: " +$('ul[name="tr"]').eq(5).children().children().text());
console.log("Getting text of childs of certail ul: " +$('ul[name="tr"]').eq(5).children().eq(2).text());
console.log("Getting no of direct children below ul # 5: " +$('ul[name="tr"]').eq(5).children().length);
console.log("Getting no of direct ul children below ul # 5: " +$('ul[name="tr"]').eq(5).children('ul').length);
console.log("Getting no of direct li children below ul # 5: " +$('ul[name="tr"]').eq(5).children('li').length);
console.log("Getting no of direct li children below child ul of ul # 5: " +$('ul[name="tr"]').eq(5).children('ul').eq(0).children('li').length);
console.log("Getting text of childs of certain ul and li: " +$('ul[name="tr"]').eq(5).children().eq(1).text());
console.log("Getting text of childs of certail ul: " +$('ul[name="tr"]').eq(5).text());
console.log("Getting text of childs of certail ul: " +$('ul[name="tr"] > li').text());
console.log("Getting text of childs of certail ul: " +$('ul[name="tr"]:not(:parent:has(ul[name="tr"]))').length);
console.log("Find all ul under certain ul " +$('ul[name="tr"]').eq(5).find('ul').length);
*/
var treeVariables = new Array();
var index = 0;
var j = 0;
var n = 0;
var newOb;
var childNo;
var next1Childs = new Array();
var next2Childs = new Array();
var next1 = 0;
var next2 = 0;

$('ul[name="tr"]')
.each(
		function(i, obj1) {
			//test

			console.log("i value is " + i)

			newObj = $(this);
			if (newObj.children('ul').length > 0) {
				while (newObj
						.children('ul').length > 0) {
					//			    console.log("The ul contains a number of uls equal to: " +newObj.find('ul').length);
					//			    console.log("The li text is: " +newObj.children('li').text());

					childNo = newObj
							.find('ul').length;
					//			    console.log("######### the node title is " +newObj.children('li').text());	

					treeVariables[j] = {
						"title" : newObj
								.children(
										'li')
								.text(),
						"lft" : index + n
								+ 1,
						"rgt" : index + n
								+ 2 + 2
								* childNo
					};
					j = j + 1;
					n = n + 1;
					(newObj.children('ul')
							.children('li'))
							.each(function(
									k, obj2) {
								console
										.log("The list text is "
												+ $(
														this)
														.text()
												+ " and the # of ul in each ul is: "
												+ newObj
														.children(
																'ul')
														.eq(
																k)
														.children(
																'ul').length);

								if (newObj
										.children(
												'ul')
										.eq(
												k)
										.children(
												'ul').length > 0) {
									console
											.log("the k value is "
													+ k);
									next1 = 1;
									next1Childs
											.push(k);
								}
								treeVariables[j] = {
									"title" : $(
											this)
											.text(),
									"lft" : index
											+ n
											+ 1,
									"rgt" : index
											+ n
											+ 2
								};
								//					console.log("&&&&&&&& The li text is " +$(this).text());
								j = j + 1;
								n = n + 2;
							});
					console
							.log("The next1Childs is: "
									+ next1Childs);
					newObj = newObj
							.children('ul');
				}
			} else {
				//			console.log("******* the node title is " +newObj.children('li').text());		
				treeVariables[j] = {
					"title" : newObj
							.children('li')
							.text(),
					"lft" : index + 1,
					"rgt" : index + 2
				};
				j = j + 1;
				index = index + 2;
			}
		});

/*	
console.log("length of treeVariables is " +treeVariables.length)
console.log("variable at 0 in treeVariable is " +treeVariables[1]["lft"])
console.log("The treeVariables is " +treeVariables);
console.log(treeVariables);
console.log("The treeVariables is ", treeVariables);
*/

});
var toggler = document.getElementsByClassName("caret");
var i;

for (i = 0; i < toggler.length; i++) {
toggler[i].addEventListener("click", function() {
this.parentElement.querySelector(".nested").classList
.toggle("selected");
this.classList.toggle("caret-down");
});
}
</script>
</html>