package capstone.probablymainserver.capstoneserver;

import java.sql.*;
import java.util.*;

public class capstone {
	public User Login(String UserID, String PassWord) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int myUID;
		String userID;
		Long tools;
		Long banned;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			if (checkUserID(UserID) == false) { // 아이디 존재 확인
				return null; // 존재하지 않는 아이디
			}
			String sql = "SELECT uid, username, tool, allergy FROM user WHERE username = ? AND userpw = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, UserID);
			stmt.setString(2, PassWord);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				User user = new User();
				myUID = rs.getInt("uid");
				userID = rs.getString("username");
				tools = rs.getLong("tool");
				banned = rs.getLong("allergy");
				user.setUid(myUID);
				user.setName(userID);
				user.setTools(tools);
				user.setBanned(banned);
				return user;
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public boolean checkUserID(String UserID) {
		String sql = "SELECT uid FROM user WHERE username = ?";

        try (
        	Connection conn = DatabaseUtil.getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        	pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
            	return true;
            } else {
            	return false;
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
	public int updateUserID(String newUserID, User u) {
		int uid = u.getUid();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			if (checkUserID(newUserID)) { // 아이디 존재 확인
				return 3; // 존재하는 아이디
			}
			
			String sql = "UPDATE user SET username = ? WHERE uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, newUserID);
			stmt.setInt(2, uid);
						
			int rows = stmt.executeUpdate();
			if (rows > 0) { // 아이디 수정 성공 여부
				u.setName(newUserID);
				return 1; // 아이디 수정 성공
			} else {
				return 2; // 아이디 수정 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public int updateUserPW(String newPassWord, User u) {
		int uid = u.getUid();
		
		Connection conn = null;
		PreparedStatement checkStmt = null;
		PreparedStatement updateStmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String checkSql = "SELECT userpw FROM user WHERE uid = ? AND userpw = ?";
			checkStmt = conn.prepareStatement(checkSql);
			checkStmt.setInt(1, uid);
			checkStmt.setString(2, newPassWord);
			ResultSet rs = checkStmt.executeQuery();
			
			if (rs.next()) {
				return 5; // 이미 사용중인 비밀번호
			}
			
			String updateSql = "UPDATE user SET userpw = ? WHERE uid = ?";
			updateStmt = conn.prepareStatement(updateSql);
			updateStmt.setString(1, newPassWord);
			updateStmt.setInt(2, uid);
						
			int rows = updateStmt.executeUpdate();
			if (rows > 0) { // 비밀번호 수정 성공 여부
				return 1; // 비밀번호 수정 성공
			} else {
				return 2; // 비밀번호 수정 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(updateStmt);
			DatabaseUtil.close(checkStmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public List<Recipe> RecipeListSearchFromDB(List<String> ingredients){
		ArrayList<Recipe> l = new ArrayList<>();
		List<Integer> resultRID = null;
		
		for (String ingr : ingredients) {
			List<Integer> current = searchCodeInDB(ingr);
			
			if (current == null) {
				continue;
			}
			
			if (resultRID == null) {
				resultRID = new ArrayList<>(current);
			} else {
				resultRID.retainAll(current);
			}
		}
		
		if (resultRID == null || resultRID.isEmpty()) {
			return new ArrayList<>();
		}
		
		for (int rid : resultRID) {
			Recipe r = new Recipe();
			r = RecipeByCode(rid);
			if (r != null) {
				l.add(r);
			}
		}
		
		return l;
	}
	
	public List<Integer> searchCodeInDB(String ingr){
		Connection conn = null;
		PreparedStatement stmt = null;
		ArrayList<Integer> list = new ArrayList<>();
		int rid;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "SELECT rid FROM community WHERE foodingredient LIKE ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + ingr + "%");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				rid = rs.getInt("rid");
				list.add(rid);
			}
			
			return list;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public Recipe RecipeByCode(int code) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		int rid = code;
		//String author;
		String name;
		String foodingredient;
		Ingredient main = new Ingredient();
		ArrayList<Ingredient> sub = new ArrayList<>();
		int maincode;
		String mainname;
		int maintype;
		int subcode;
		int subtype;
		Long tools;
		String foodrecipe;
		ArrayList<String> recipes = new ArrayList<>();
		String time;
		int uid;
		Recipe recipe = new Recipe();
		
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT rid, foodname, foodingredient, mainingredientcode, tool, foodrecipe, foodtype, time, uid FROM community WHERE rid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, rid);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				code = rs.getInt("rid");
				name = rs.getString("foodname");
				foodingredient = rs.getString("foodingredient");
				maincode = rs.getInt("mainingredientcode");
				tools = rs.getLong("tool");
				foodrecipe = rs.getString("foodrecipe");
				time = rs.getString("time");
				uid = rs.getInt("uid");
				//author = checkUserName(uid);
				
				recipe.setCode(code);
				//recipe.setAuthor(author);
				recipe.setName(name);
				recipe.setTools(tools);
				recipes.add(foodrecipe);
				recipe.setRecipe(recipes);
				recipe.setTime(time);
				mainname = checkIngredientCode(maincode);
				maintype = checkIngredientType(maincode);
				main.setCode(maincode);
				main.setName(mainname);
				main.setType(maintype);
				main.setCount(null);
				main.setDescription(null);
				recipe.addMainIngredients(main);
				for (String subname : foodingredient.split(",")) {
	                Ingredient ing = new Ingredient();
	                ing.setName(subname.trim());
	                subcode = checkIngredientName(subname);
	                ing.setCode(subcode);
	                subtype = checkIngredientType(subcode);
	                ing.setType(subtype);
	                ing.setCount(null);
	                ing.setDescription(null);
	                sub.add(ing);
	            }
				recipe.setSubIngredients(sub);
				
				return recipe;
			} else {
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public String checkUserName(int userID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT username FROM user WHERE uid = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("username");
			} else {
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public int RecipeAdd(Recipe recipe, int userID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String FoodName = recipe.getName();
			
			ArrayList<Ingredient> main = recipe.getMainIngredients();
			ArrayList<Ingredient> sub = recipe.getSubIngredients();
			
			StringBuilder sb = new StringBuilder();
			
			for (Ingredient ingr : main) {
				sb.append(ingr.getName()).append(",");
			}
			
			for (Ingredient ingr : sub) {
				sb.append(ingr.getName()).append(",");
			}
			
			if (sb.length() > 0) {
				sb.setLength(sb.length() -1);
			}
			
			String FoodIngredients = sb.toString();
			String mainname = !main.isEmpty() ? main.get(0).getName() : null;
			Long tool = recipe.getTools();
			ArrayList<String> FoodRecipe = recipe.getRecipe();
			String foodrecipe = String.join("\n", FoodRecipe);
			String time = recipe.getTime();
			int maincode = (mainname != null) ? checkIngredientName(mainname) : 0;
			
			String sql = "INSERT INTO community (foodname, foodingredient, mainingredientcode, tool, foodrecipe, foodtype, time, uid, viewcount, savecount, uploaddate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, FoodName);
			stmt.setString(2, FoodIngredients);
			stmt.setInt(3, maincode);
			stmt.setLong(4, tool);
			stmt.setString(5, foodrecipe);
			stmt.setInt(6, 0);
			stmt.setString(7, time);
			stmt.setInt(8, userID);
			stmt.setInt(9, 0);
			stmt.setInt(10, 0);
			stmt.setDate(11, new java.sql.Date(System.currentTimeMillis()));
			
			int rows = stmt.executeUpdate();
			if(rows > 0) { // 레시피 등록 성공 여부
				return 1; // 성공
			} else {
				return 2; // 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public int RecipeDelete(int recipeCode, int userID) {
		int uid = userID;
		int rid = recipeCode;
		
		int checkUid = 0;
		
		Connection conn = null;
		PreparedStatement checkStmt = null;
		PreparedStatement updateStmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String checkSql = "SELECT uid FROM community WHERE rid = ?";
			checkStmt = conn.prepareStatement(checkSql);
			checkStmt.setInt(1, rid);
			ResultSet rs = checkStmt.executeQuery();
			if(rs.next()) {
				checkUid = rs.getInt("uid");
			}
			rs.close();
			if(checkUid != uid) {
				return 4; // 실패 : 본인 레시피가 아님
			}
			
			String updateSql = "DELETE FROM community WHERE rid = ? AND uid = ?";
			updateStmt = conn.prepareStatement(updateSql);
			updateStmt.setInt(1, rid);
			updateStmt.setInt(2, uid);
			
			int rows = updateStmt.executeUpdate();
			if(rows > 0) { // 레시피 삭제 성공 여부
				return 1; // 성공
			} else {
				return 2; // 실패
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(updateStmt);
			DatabaseUtil.close(checkStmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public int RecipeUpdate(Recipe recipeData, int recipeCode) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String foodName = null;
		
		int maincode = 0;
		Long tool;
		ArrayList<String> FoodRecipe = null;
		String foodrecipe;
		String time;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			foodName = recipeData.getName();
			ArrayList<Ingredient> main = recipeData.getMainIngredients();
			ArrayList<Ingredient> sub = recipeData.getSubIngredients();
			StringBuilder sb = new StringBuilder();
			
			for (Ingredient ingr : main) {
				sb.append(ingr.getName()).append(",");
			}
			
			for (Ingredient ingr : sub) {
				sb.append(ingr.getName()).append(",");
			}
			
			if (sb.length() > 0) {
				sb.setLength(sb.length() -1);
			}
			
			String FoodIngredients = sb.toString();
			String mainname = !main.isEmpty() ? main.get(0).getName() : null;
			tool = recipeData.getTools();
			FoodRecipe = recipeData.getRecipe();
			foodrecipe = FoodRecipe.toString();
			time = recipeData.getTime();
			maincode = (mainname != null) ? checkIngredientName(mainname) : 0;
			
			String sql = "UPDATE community SET foodname = ?, foodingredient = ?, mainingredientcode = ?, tool = ?, foodrecipe = ?, time = ? WHERE rid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, foodName);
			stmt.setString(2, FoodIngredients);
			stmt.setInt(3, maincode);
			stmt.setLong(4, tool);
			stmt.setString(5, foodrecipe);
			stmt.setString(6, time);
			stmt.setInt(7, recipeCode);
			
			int rows = stmt.executeUpdate();
			if(rows > 0) { // 레시피 수정 성공 여부
				return 1; // 성공
			} else {
				return 2; // 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public int checkIngredientName(String ingr) {
		int code = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT ingredientno FROM ingredient WHERE ingredientname = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, ingr);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				code = rs.getInt("ingredientno");
				return code;
			} else {
				return 0;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public String checkIngredientCode(int ingr) {
		String name = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT ingredientname FROM ingredient WHERE ingredientno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ingr);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				name = rs.getString("ingredientname");
				return name;
			} else {
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public int checkIngredientType(int ingr) {
		int type = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT ingredienttype FROM ingredient WHERE ingredientno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ingr);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				type = rs.getInt("ingredienttype");
				return type;
			} else {
				return 0;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public List<Recipe> RecipeListAdded(int userID){
		Connection conn = null;
		PreparedStatement stmt = null;
		
		int code;
		String name;
		String ingredient;
		int maincode;
		String mainname;
		int maintype;
		int subcode;
		int subtype;
		Long tools;
		String recipe;
		int foodtype;
		String time;
		ArrayList<Recipe> l = new ArrayList<>();
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "SELECT rid, foodname, foodingredient, mainingredientcode, tool, foodrecipe, foodtype, time FROM community WHERE uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Ingredient main = new Ingredient();
				ArrayList<Ingredient> sub = new ArrayList<>();
				Recipe r = new Recipe();
				ArrayList<String> recipes = new ArrayList<>();
				
				code = rs.getInt("rid");
				name = rs.getString("foodname");
				ingredient = rs.getString("foodingredient");
				maincode = rs.getInt("mainingredientcode");
				tools = rs.getLong("tool");
				recipe = rs.getString("foodrecipe");
				foodtype = rs.getInt("foodtype");
				time = rs.getString("time");
				
				mainname = checkIngredientCode(maincode);
				maintype = checkIngredientType(maincode);
				main.setCode(maincode);
				main.setName(mainname);
				main.setType(maintype);
				main.setCount(null);
				main.setDescription(null);
				for (String subname : ingredient.split(",")) {
	                Ingredient ing = new Ingredient();
	                ing.setName(subname.trim());
	                subcode = checkIngredientName(subname);
	                ing.setCode(subcode);
	                subtype = checkIngredientType(subcode);
	                ing.setType(subtype);
	                ing.setCount(null);
	                ing.setDescription(null);
	                sub.add(ing);
	            }
				recipes.add(recipe);
				
				r.setCode(code);
				r.addMainIngredients(main);
				r.setName(name);
				r.setSubIngredients(sub);
				r.setTools(tools);
				r.setRecipe(recipes);
				r.setTime(time);
				
				l.add(r);
			}
			
			return l;
			
			
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public int RecipeLike(int code, int userID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String FoodName = null;
		
		int maincode = 0;
		String mainname;
		Long tool;
		ArrayList<String> FoodRecipe = null;
		String foodrecipe;
		int uid = userID;
		String time;
		Recipe recipe = RecipeByCode(code);
		
		try {
			if(checkLike(code, userID) == false) {
				return 5; // 보유 중인 좋아요
			}
			
			conn = DatabaseUtil.getConnection();
			
			FoodName = recipe.getName();
			ArrayList<Ingredient> main = recipe.getMainIngredients();
			ArrayList<Ingredient> sub = recipe.getSubIngredients();
			StringBuilder sb = new StringBuilder();
			
			for (Ingredient ingr : main) {
				sb.append(ingr.getName()).append(",");
			}
			
			for (Ingredient ingr : sub) {
				sb.append(ingr.getName()).append(",");
			}
			
			if (sb.length() > 0) {
				sb.setLength(sb.length() -1);
			}
			
			String FoodIngredients = sb.toString();
			
			mainname = !main.isEmpty() ? main.get(0).getName() : null;
			maincode = (mainname != null) ? checkIngredientName(mainname) : 0;
			tool = recipe.getTools();
			FoodRecipe = recipe.getRecipe();
			foodrecipe = String.join("\n", FoodRecipe);
			time = recipe.getTime();
			
			String sql = "INSERT INTO recipe (foodname, foodingredient, mainingredientcode, tool, foodrecipe, foodtype, time, uid, checkcode, rid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, FoodName);
			stmt.setString(2, FoodIngredients);
			stmt.setInt(3, maincode);
			stmt.setLong(4, tool);
			stmt.setString(5, foodrecipe);
			stmt.setInt(6, 0);
			stmt.setString(7, time);
			stmt.setInt(8, uid);
			stmt.setInt(9, 1); // ai면 0, 좋아요면 1
			stmt.setInt(10, code);
			
			int rows = stmt.executeUpdate();
			if(rows > 0) { // 레시피 좋아요 성공 여부
				// increaseLike(code); -> 좋아요 증가
				return 1; // 성공
			} else {
				return 2; // 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public boolean checkLike(int code, int userID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT rno FROM recipe WHERE uid = ? AND rid = ? AND checkcode = 1";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userID);
			stmt.setInt(2, code);
			ResultSet rs = stmt.executeQuery();
			return !rs.next();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false; // 오류
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public void increaseLike(int code) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "UPDATE community SET savecount = savecount + 1 WHERE rid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, code);
			stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public void decreaseLike(int code) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "UPDATE community SET savecount = savecount - 1 WHERE rid = ? AND savecount > 0";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, code);
			stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public int RecipeUnlike(int code, int userID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "DELETE FROM recipe WHERE rid = ? AND uid = ? AND checkcode = 1";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, code);
			stmt.setInt(2, userID);
			
			int rows = stmt.executeUpdate();
			if(rows > 0) { // 종아요 삭제 성공 여부
				// decreaseLike(code); -> 좋아요 감소
				return 1; // 성공
			} else {
				return 2; // 실패
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 2; //실패
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public List<Recipe> RecipeListLiked(int userID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		int code;
		String name = null;
		String ingredient;
		int maincode;
		String mainname;
		int maintype;
		int subcode;
		int subtype;
		Long tools;
		String recipe;
		int foodtype;
		String time;
		ArrayList<Recipe> l = new ArrayList<>();
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "SELECT rno, foodname, foodingredient, mainingredientcode, tool, foodrecipe, foodtype, time FROM recipe WHERE uid = ? AND checkcode = 1";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Recipe r = new Recipe();
				ArrayList<String> recipes = new ArrayList<>();
				Ingredient main = new Ingredient();
				ArrayList<Ingredient> sub = new ArrayList<>();
				
				code = rs.getInt("rno");
				name = rs.getString("foodname");
				ingredient = rs.getString("foodingredient");
				maincode = rs.getInt("mainingredientcode");
				tools = rs.getLong("tool");
				recipe = rs.getString("foodrecipe");
				foodtype = rs.getInt("foodtype");
				time = rs.getString("time");
				
				mainname = checkIngredientCode(maincode);
				maintype = checkIngredientType(maincode);
				main.setCode(maincode);
				main.setName(mainname);
				main.setType(maintype);
				main.setCount(null);
				main.setDescription(null);
				for (String subname : ingredient.split(",")) {
	                Ingredient ing = new Ingredient();
	                ing.setName(subname.trim());
	                subcode = checkIngredientName(subname);
	                ing.setCode(subcode);
	                subtype = checkIngredientType(subcode);
	                ing.setType(subtype);
	                ing.setCount(null);
	                ing.setDescription(null);
	                sub.add(ing);
	            }
				recipes.add(recipe);
				
				r.setCode(code);
				r.addMainIngredients(main);
				r.setName(name);
				r.setSubIngredients(sub);
				r.setTools(tools);
				r.setRecipe(recipes);
				r.setTime(time);
				
				l.add(r);
			}
			
			return l;
			
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
}
